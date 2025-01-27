package com.j2e.library.service;

import com.j2e.library.entity.Book;
import com.j2e.library.entity.Borrowing;
import com.j2e.library.entity.User;
import com.j2e.library.exceptions.*;
import com.j2e.library.repository.IBookRepository;
import com.j2e.library.repository.IBorrowingRepository;
import com.j2e.library.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BorrowingService {
    private static final int BORROWING_LIMIT = 3;
    private final IBorrowingRepository borrowingRepository;
    private final IBookRepository bookRepository;
    private final IUserRepository userRepository;

    //CREATE
    public void create(Borrowing borrow) throws BookUnavailableException, BookNotFoundException, UserNotFoundException, BorrowingLimitExceededException {
        //Verify book exist
        if (borrow.getBook() != null) {
            if (borrow.getBook().getId() != null && borrow.getBook().getId() > 0) {
                //If there is an ID
                bookRepository.findById(borrow.getBook().getId()).ifPresentOrElse(
                        book -> {
                            if (!book.isAvailable())
                                throw new BookUnavailableException();
                        }, () -> {
                            throw new BookNotFoundException();
                        }
                );
            } else if (borrow.getBook().getTitle() != null && !borrow.getBook().getTitle().isEmpty() && borrow.getBook().getAuthor() != null && !borrow.getBook().getAuthor().isEmpty()) {
                //Searching by title and author
                List<Book> booksToFound = bookRepository.findByTitleAndAuthor(borrow.getBook().getTitle(), borrow.getBook().getAuthor());
                boolean bookFound = false;
                for (Book book : booksToFound) {
                    if (book.isAvailable()) {
                        borrow.getBook().setId(book.getId());
                        bookFound = true;
                        break;
                    }
                }
                if (!bookFound) throw new BookNotFoundException();
            } else {
                throw new BookNotFoundException();
            }
        } else {
            throw new BookNotFoundException();
        }

        //Verify User exist
        if (borrow.getUser() != null) {
            if (borrow.getUser().getId() != null && borrow.getUser().getId() > 0) {
                //If there is an ID
                if (userRepository.findById(borrow.getUser().getId()).isEmpty())
                    throw new UserNotFoundException();

            } else if (borrow.getUser().getEmail() != null && !borrow.getUser().getEmail().isEmpty()) {
                //Searching by email
                userRepository.findByEmail(borrow.getUser().getEmail()).ifPresentOrElse(user -> borrow.getUser().setId(user.getId()), () -> {
                    throw new UserNotFoundException();
                });
            } else {
                throw new UserNotFoundException();
            }
        } else {
            throw new UserNotFoundException();
        }

        //OK : Book and User found
        //Verify number of Borrowing Limit
        if (borrowingRepository.countByUserAndReturnDateIsNull(borrow.getUser()) >= BORROWING_LIMIT)
            throw new BorrowingLimitExceededException();

        //User can do the borrowing

        //Set Borrow Date
        if (borrow.getBorrowDate() == null)
            borrow.setBorrowDate(LocalDate.now());

        //Toggle book Available
        bookRepository.findById(borrow.getBook().getId()).ifPresent(book -> {
            book.setAvailable(false);
            bookRepository.save(book);
        });

        borrowingRepository.save(borrow);
    }

    //READ
    public List<Borrowing> getAll() {
        return borrowingRepository.findAll();
    }

    public List<Borrowing> getAllOfUser(Long id) throws UserNotFoundException{
        Optional<User> userToFind = userRepository.findById(id);
        if(userToFind.isPresent())
            return borrowingRepository.findByUser(userToFind.get());
        else throw new UserNotFoundException();
    }

    public List<Borrowing> getCurrentOfUser(Long id) throws UserNotFoundException {
        Optional<User> userToFind = userRepository.findById(id);
        if(userToFind.isPresent())
            return borrowingRepository.findByUserAndReturnDateIsNull(userToFind.get());
        else throw new UserNotFoundException();
    }

    //UPDATE
    public void updateReturnDate(Long id, LocalDate returned) throws BookNotFoundException, BorrowingReturnDateException,BorrowingNotFoundException{
        borrowingRepository.findById(id).ifPresentOrElse(borrow -> {
            if(borrow.getBorrowDate().isAfter(returned)){
                throw new BorrowingReturnDateException();
            } else {
                borrow.setReturnDate(returned);
                borrowingRepository.save(borrow);
                //Change book available status
                bookRepository.findById(borrow.getId()).ifPresentOrElse(book->{
                    book.setAvailable(true);
                    bookRepository.save(book);
                },()->{throw new BookNotFoundException();});
            }

        }, () -> {
            throw new BorrowingNotFoundException();
        });
    }

    //DELETE
    public void delete(Long id) throws BorrowingNotFoundException, BookNotFoundException {
        borrowingRepository.findById(id).ifPresentOrElse(borrow->{
            bookRepository.findById(borrow.getBook().getId()).ifPresentOrElse(book->{
                book.setAvailable(true);
                bookRepository.save(book);
            },()->{throw new BookNotFoundException();});
        }, () -> {
            throw new BorrowingNotFoundException();
        });
    }
}
