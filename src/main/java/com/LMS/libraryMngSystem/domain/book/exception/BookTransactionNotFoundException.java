package com.LMS.libraryMngSystem.domain.book.exception;

public class BookTransactionNotFoundException extends RuntimeException{
    public BookTransactionNotFoundException(String message) {
        super(message);
    }
}
