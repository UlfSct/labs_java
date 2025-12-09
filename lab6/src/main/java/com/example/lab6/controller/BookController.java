package com.example.lab6.controller;

import com.example.lab6.model.Book;
import com.example.lab6.request.book.BookCreateRequest;
import com.example.lab6.request.book.BookUpdateRequest;
import com.example.lab6.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/selectors/books")
    public ResponseEntity<?> selector() {
        return new ResponseEntity<>(service.getSelector(), HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<?> list(
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "size", required = false) Integer size,
        @RequestParam(value = "search", required = false) String search
    ) {
        return new ResponseEntity<>(service.getAllPaginated(page, size, search), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> retrieve(@PathVariable Integer id) {
        Book response = service.findItemById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/authors/{authorId}/books")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable Integer authorId) {
        return new ResponseEntity<>(service.getBooksByAuthorId(authorId), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<?> create(@RequestBody BookCreateRequest data) {
        return new ResponseEntity<>(service.saveItem(data), HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody BookUpdateRequest data) {
        return new ResponseEntity<>(service.updateItem(id, data), HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(service.deleteItemById(id), HttpStatus.OK);
    }
}