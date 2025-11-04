package com.example.booklibrary.controller;

import com.example.booklibrary.model.Book;
import com.example.booklibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(service.createBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveBook(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getBookById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> retrieveBooks() {
        return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        return new ResponseEntity<>(service.deleteBookById(id), HttpStatus.OK);
    }
}