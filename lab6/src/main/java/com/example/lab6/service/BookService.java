package com.example.lab6.service;

import com.example.lab6.errors.ResourceNotFoundException;
import com.example.lab6.model.Author;
import com.example.lab6.model.Book;
import com.example.lab6.model.BookInfo;
import com.example.lab6.repository.BookInfoRepository;
import com.example.lab6.repository.BookRepository;
import com.example.lab6.request.book.BookCreateRequest;
import com.example.lab6.request.book.BookSelectorRequest;
import com.example.lab6.request.book.BookUpdateRequest;
import com.example.lab6.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookValidator validator = new BookValidator();
    private final AuthorService authorService;
    private final BookInfoRepository bookInfoRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService, BookInfoRepository bookInfoRepository) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.bookInfoRepository = bookInfoRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public List<BookSelectorRequest> getSelector() {
        return getAll().stream().map(
                item -> new BookSelectorRequest(
                        item.getId(),
                        item.getTitle() + " (" + item.getAuthorFullName() + ')'
                )
        ).toList();
    }

    public Page<Book> getAllPaginated(Integer page, Integer size, String search) {
        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (size != null && size > 0) ? size : 25;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if (search != null) {
            return bookRepository.findByTitleContainingIgnoreCase(search.trim(), pageable);
        }

        return bookRepository.findAll(pageable);
    }

    public Book findItemById(Integer id) {
        Optional<Book> item = bookRepository.findById(id);
        if (item.isEmpty()) throw new ResourceNotFoundException("Книга с ID " + id + " не найдена");
        return item.get();
    }

    public Book saveItem(BookCreateRequest data) {
        validator.validate(data);

        Author author = authorService.findItemById(data.getAuthorId());

        Book savedBook = bookRepository.save(
            new Book(
                data.getTitle(),
                data.getYear(),
                author
            )
        );

        if (data.getIsbn() != null) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setIsbn(data.getIsbn());
            bookInfo.setBook(savedBook);
            bookInfoRepository.save(bookInfo);
            savedBook.setBookInfo(bookInfo);
        }

        return savedBook;
    }

    public Book updateItem(Integer id, BookUpdateRequest data) {
        Book item = findItemById(id);
        validator.validate(data);

        Author author = authorService.findItemById(data.getAuthorId());

        item.setTitle(data.getTitle());
        item.setYear(data.getYear());
        item.setAuthor(author);

        Book updatedBook = bookRepository.save(item);
        BookInfo bookInfo = bookInfoRepository.findByBookId(id);

        if (data.getIsbn() != null) {
            if (bookInfo == null) {
                bookInfo = new BookInfo();
                bookInfo.setBook(updatedBook);
            }
            bookInfo.setIsbn(data.getIsbn().trim());
            bookInfoRepository.save(bookInfo);
            updatedBook.setBookInfo(bookInfo);
            return updatedBook;
        }

        bookInfoRepository.delete(bookInfo);
        updatedBook.setBookInfo(null);
        return updatedBook;
    }

    public Book deleteItemById(Integer id) {
        Book item = findItemById(id);
        bookRepository.deleteById(id);
        return item;
    }

    public List<Book> getBooksByAuthorId(Integer authorId) {
        authorService.findItemById(authorId);
        return bookRepository.findAll().stream()
                .filter(book -> book.getAuthor().getId().equals(authorId))
                .toList();
    }
}