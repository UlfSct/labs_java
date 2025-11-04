package com.example.booklibrary.repository;

import com.example.booklibrary.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class BookRepository
{
    private final List<Book> books = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public BookRepository()
    {
        books.add(new Book(idCounter.getAndIncrement(), "Над пропостью поржи", "Джером Д?? Сэллинджер", 1970));
        books.add(new Book(idCounter.getAndIncrement(), "Краснее красного", "Вера Камша", 1978));
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Optional<Book> getBookById(Integer id)
    {
        Optional<Book> res = Optional.empty();
        for (Book book : books)
        {
            if (Objects.equals(book.getId(), id))
            {
                res = Optional.of(book);
                break;
            }
        }
        return res;
    }

    public Optional<Book> deleteBookById(Integer id)
    {
        Optional<Book> res = Optional.empty();
        for (int i = 0; i < books.size(); i++)
        {
            if (Objects.equals(books.get(i).getId(), id))
            {
                res = Optional.of(books.get(i));
                books.remove(i);
                break;
            }
        }
        return res;
    }

    public Book save(Book book)
    {
        book.setId(idCounter.getAndIncrement());
        books.add(book);
        return book;
    }
}
