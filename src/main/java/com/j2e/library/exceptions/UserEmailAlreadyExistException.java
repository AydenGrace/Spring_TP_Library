package com.j2e.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserEmailAlreadyExistException extends RuntimeException {
    public UserEmailAlreadyExistException() {
        super("Error : User Email already exist.");
    }
    public UserEmailAlreadyExistException(String message) {
        super(message);
    }
}
