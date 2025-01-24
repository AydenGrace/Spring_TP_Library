package com.j2e.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Book not found")
public class BookNotFoundException extends RuntimeException {
  public BookNotFoundException() {
    super("Error : book not found");
  }
    public BookNotFoundException(String message) {
        super(message);
    }
}
