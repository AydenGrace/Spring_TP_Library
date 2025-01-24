package com.j2e.library.repository;

import com.j2e.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<Book,Long> {
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByIsAvailable(boolean available);
    Optional<Book> findByTitleAndAuthor(String title, String author);
}
