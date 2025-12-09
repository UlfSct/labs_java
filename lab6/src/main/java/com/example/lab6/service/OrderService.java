package com.example.lab6.service;

import com.example.lab6.errors.ResourceNotFoundException;
import com.example.lab6.model.Book;
import com.example.lab6.model.Order;
import com.example.lab6.repository.BookRepository;
import com.example.lab6.repository.OrderRepository;
import com.example.lab6.request.order.OrderCreateRequest;
import com.example.lab6.request.order.OrderUpdateRequest;
import com.example.lab6.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderValidator validator = new OrderValidator();

    @Autowired
    public OrderService(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    public Page<Order> getAllPaginated(Integer page, Integer size, String search) {
        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (size != null && size > 0) ? size : 25;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if (search != null && !search.trim().isEmpty()) {
            return orderRepository.findByClientContainingIgnoreCase(search.trim(), pageable);
        }

        return orderRepository.findAll(pageable);
    }

    public Order findItemById(Integer id) {
        Optional<Order> item = orderRepository.findById(id);
        if (item.isEmpty()) throw new ResourceNotFoundException("Заказ с ID " + id + " не найден");
        return item.get();
    }

    public Order saveItem(OrderCreateRequest data) {
        validator.validate(data);

        List<Book> books = getBooksByIds(data.getBookIds());

        Order order = new Order(data.getClient().trim(), books);
        return orderRepository.save(order);
    }

    public Order updateItem(Integer id, OrderUpdateRequest data) {
        validator.validate(data);

        Order item = findItemById(id);

        List<Book> books = getBooksByIds(data.getBookIds());

        item.setClient(data.getClient().trim());
        item.setBooks(books);

        return orderRepository.save(item);
    }

    public Order deleteItemById(Integer id) {
        Order item = findItemById(id);
        item.getBooks().clear();
        orderRepository.save(item);
        orderRepository.deleteById(id);
        return item;
    }

    private List<Book> getBooksByIds(List<Integer> bookIds) {
        List<Book> books = new ArrayList<>();

        for (Integer bookId : bookIds) {
            Optional<Book> bookOpt = bookRepository.findById(bookId);
            if (bookOpt.isEmpty()) continue;
            books.add(bookOpt.get());
        }

        return books;
    }
}