package com.j2e.library.repository;

import com.j2e.library.entity.Book;
import com.j2e.library.entity.Borrowing;
import com.j2e.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IBorrowingRepository extends JpaRepository<Borrowing,Long> {
    List<Borrowing> findByUser(User user);
    List<Borrowing> findByUserAndReturnDateIsNull(User user);
    long countByUserAndReturnDateIsNull(User user);
}
