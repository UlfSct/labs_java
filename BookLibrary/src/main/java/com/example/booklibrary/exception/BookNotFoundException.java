package com.example.booklibrary.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Integer id) {
        super("Книга с ID " + id + " не найдена");
    }
}
