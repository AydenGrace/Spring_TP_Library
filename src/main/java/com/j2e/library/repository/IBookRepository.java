package com.j2e.library.repository;

import com.j2e.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book,Long> {
    List<Book> findByIsAvailable(boolean available);
    List<Book> findByTitleAndAuthor(String title, String author);
}
