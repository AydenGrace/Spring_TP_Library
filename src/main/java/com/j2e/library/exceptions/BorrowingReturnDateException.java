package com.j2e.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "The date of return must be after the borrow's date.")
public class BorrowingReturnDateException extends RuntimeException {
    public BorrowingReturnDateException() {
        super("Error : The date of return must be after the borrow's date.");
    }
    public BorrowingReturnDateException(String message) {
        super(message);
    }
}
