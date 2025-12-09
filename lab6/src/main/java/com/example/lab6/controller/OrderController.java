package com.example.lab6.controller;

import com.example.lab6.model.Order;
import com.example.lab6.request.order.OrderCreateRequest;
import com.example.lab6.request.order.OrderUpdateRequest;
import com.example.lab6.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/orders")
    public ResponseEntity<?> list(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "search", required = false) String search
    ) {
        return new ResponseEntity<>(service.getAllPaginated(page, size, search), HttpStatus.OK);
    }


    @GetMapping("/orders/{id}")
    public ResponseEntity<?> retrieve(@PathVariable Integer id) {
        Order response = service.findItemById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> create(@RequestBody OrderCreateRequest data) {
        return new ResponseEntity<>(service.saveItem(data), HttpStatus.CREATED);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody OrderUpdateRequest data) {
        return new ResponseEntity<>(service.updateItem(id, data), HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(service.deleteItemById(id), HttpStatus.OK);
    }
}