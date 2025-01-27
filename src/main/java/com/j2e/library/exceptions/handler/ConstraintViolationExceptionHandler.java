package com.j2e.library.exceptions.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        StringBuilder messageFields = new StringBuilder();

        for (ObjectError allError : ex.getAllErrors()) {
            System.out.println("Error on validation : "+allError.getDefaultMessage());
            if(messageFields.isEmpty()) messageFields.append(allError.getDefaultMessage());
            else messageFields.append(", ").append(allError.getDefaultMessage());
        }

        messageFields = new StringBuilder("[" + messageFields + "]");

        return new ResponseEntity<>("Error : "+messageFields, HttpStatus.FORBIDDEN);
    }
}
