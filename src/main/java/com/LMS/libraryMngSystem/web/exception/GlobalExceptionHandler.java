package com.LMS.libraryMngSystem.web.exception;

import com.LMS.libraryMngSystem.domain.book.exception.BookUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(BookUnavailableException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponse handleNotFound(BookUnavailableException ex) {
//        return new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
