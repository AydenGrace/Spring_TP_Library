package com.j2e.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingDto {
    private UserDto user;
    private BookDto book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
