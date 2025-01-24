package com.j2e.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "Book not available")
public class BookUnavailableException extends RuntimeException {
    public BookUnavailableException(){super("Error : book not available");}
    public BookUnavailableException(String message) {
        super(message);
    }
}
