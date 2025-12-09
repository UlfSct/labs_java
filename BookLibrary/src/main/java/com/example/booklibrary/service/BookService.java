package com.example.booklibrary.service;

import com.example.booklibrary.exception.BookNotFoundException;
import com.example.booklibrary.model.Book;
import com.example.booklibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService
{
    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository bookRepository)
    {
        this.repository = bookRepository;
    }

    public List<Book> getAllBooks()
    {
        return repository.getAllBooks();
    }

    public Book getBookById(Integer id)
    {
        return repository.getBookById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book deleteBookById(Integer id) {
        return repository.deleteBookById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book createBook(Book book) {
        return repository.save(book);
    }
}