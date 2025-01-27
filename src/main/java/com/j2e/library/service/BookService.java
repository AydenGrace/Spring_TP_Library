package com.j2e.library.service;

import com.j2e.library.dto.BookDto;
import com.j2e.library.dto.mapper.BookMapper;
import com.j2e.library.entity.Book;
import com.j2e.library.exceptions.BookNotFoundException;
import com.j2e.library.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {
    private final IBookRepository bookRepository;

    //CREATE
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    //READ
    public List<BookDto> getAll() {
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : bookRepository.findAll()) {
            bookDtos.add(BookMapper.toDto(book));
        }
        return bookDtos;
    }

    public BookDto getById(Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return BookMapper.toDto(book.get());
        } else {
            throw new BookNotFoundException();
        }
    }

    public List<Book> getByIsAvailable(boolean available) {
        return bookRepository.findByIsAvailable(available);
    }

    //UPDATE
    public void update(Long id, Book newBook) throws BookNotFoundException {
        bookRepository.findById(id).ifPresentOrElse(bookFound -> {
            if(newBook.getTitle() != null && !newBook.getTitle().equals(bookFound.getTitle()))
                bookFound.setTitle(newBook.getTitle());
            if(newBook.getAuthor() != null && !newBook.getAuthor().equals(bookFound.getAuthor()))
                bookFound.setAuthor(newBook.getAuthor());
            bookRepository.save(bookFound);
        }, () -> {
            throw new BookNotFoundException();
        });
    }

    public void toggleAvailable(Long id) throws BookNotFoundException {
        bookRepository.findById(id).ifPresentOrElse(bookFound -> {
            bookFound.setAvailable(!bookFound.isAvailable());
            bookRepository.save(bookFound);
        }, () -> {
            throw new BookNotFoundException();
        });
    }

    //DELETE
    public void delete(Long id) throws BookNotFoundException{
        bookRepository.findById(id).ifPresentOrElse(bookRepository::delete, () -> {
            throw new BookNotFoundException();
        });
    }
}
