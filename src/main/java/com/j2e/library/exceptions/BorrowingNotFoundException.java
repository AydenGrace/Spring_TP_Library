package com.j2e.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Borrowing not found")
public class BorrowingNotFoundException extends RuntimeException {
    public BorrowingNotFoundException() {
        super("Error : Borrowing not found");
    }
    public BorrowingNotFoundException(String message) {
        super(message);
    }
}
