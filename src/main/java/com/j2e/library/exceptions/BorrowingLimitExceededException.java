package com.j2e.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "Borrowing limit exceeded")
public class BorrowingLimitExceededException extends RuntimeException {
    public BorrowingLimitExceededException() {
        super("Error : borrowing limit exceeded");
    }
    public BorrowingLimitExceededException(String message) {
        super(message);
    }
}
