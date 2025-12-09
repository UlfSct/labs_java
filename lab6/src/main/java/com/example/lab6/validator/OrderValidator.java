package com.example.lab6.validator;

import com.example.lab6.errors.InvalidDataException;
import com.example.lab6.request.order.OrderCreateRequest;
import com.example.lab6.request.order.OrderUpdateRequest;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class OrderValidator {

    private HashMap<String, String> getOrderDefaultErrors(String client, List<Integer> bookIds) {
        HashMap<String, String> errors = new HashMap<>();

        if (client == null) {
            errors.put("client", "Обязательное поле");
        } else {
            String trimmedClient = client.trim();
            if (trimmedClient.isEmpty()) {
                errors.put("client", "Клиент не может быть пустой строкой");
            } else if (trimmedClient.length() < 2) {
                errors.put("client", "Имя клиента должно содержать минимум 2 символа");
            } else if (trimmedClient.length() > 250) {
                errors.put("client", "Имя клиента не может содержать больше 250 символов");
            }
        }

        if (bookIds == null || bookIds.isEmpty()) {
            errors.put("bookIds", "Заказ должен содержать хотя бы одну книгу");
        } else {
            List<String> invalidBookIds = new ArrayList<>();
            for (int i = 0; i < bookIds.size(); i++) {
                Integer bookId = bookIds.get(i);
                if (bookId == null || bookId <= 0) {
                    invalidBookIds.add(bookId == null ? "null" : bookId.toString());
                }
            }

            if (!invalidBookIds.isEmpty()) {
                errors.put("bookIds", "Некорректные ID книг: " + invalidBookIds);
            }
        }

        return errors;
    }

    public void validate(OrderCreateRequest data) {
        HashMap<String, String> errors = getOrderDefaultErrors(data.getClient(), data.getBookIds());
        if (!errors.isEmpty()) throw new InvalidDataException(errors);
    }

    public void validate(OrderUpdateRequest data) {
        HashMap<String, String> errors = getOrderDefaultErrors(data.getClient(), data.getBookIds());
        if (!errors.isEmpty()) throw new InvalidDataException(errors);
    }
}