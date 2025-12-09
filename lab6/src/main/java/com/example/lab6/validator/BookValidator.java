package com.example.lab6.validator;

import com.example.lab6.errors.InvalidDataException;
import com.example.lab6.request.book.BookCreateRequest;
import com.example.lab6.request.book.BookUpdateRequest;

import java.time.Year;
import java.util.HashMap;

public class BookValidator {

    private HashMap<String, String> getBookDefaultErrors(String title, Integer year, Integer authorId, String isbn) {
        HashMap<String, String> errors = new HashMap<>();
        int currentYear = Year.now().getValue();

        if (title == null) errors.put("title", "Обязательное поле");
        else if (title.trim().isEmpty()) errors.put("title", "Название не может быть пустым");
        else if (title.length() < 2) errors.put("title", "Название должно содержать минимум 2 символа");
        else if (title.length() > 255) errors.put("title", "Название не может содержать больше 255 символов");

        if (year == null) errors.put("year", "Обязательное поле");
        else if (year < 0) errors.put("year", "Год не может быть отрицательным");
        else if (year > currentYear + 5) errors.put("year", "Год не может быть больше " + (currentYear + 5));

        if (authorId == null) errors.put("authorId", "Обязательное поле");
        else if (authorId <= 0) errors.put("authorId", "ID автора должен быть положительным числом");

        if (isbn != null && isbn.length() > 17) errors.put("isbn", "Значение не должно превышать 17 символов");
        return errors;
    }

    public void validate(BookCreateRequest data) {
        HashMap<String, String> errors = getBookDefaultErrors(data.getTitle(), data.getYear(), data.getAuthorId(), data.getIsbn());
        if (!errors.isEmpty()) throw new InvalidDataException(errors);
    }

    public void validate(BookUpdateRequest data) {
        HashMap<String, String> errors = getBookDefaultErrors(data.getTitle(), data.getYear(), data.getAuthorId(), data.getIsbn());
        if (!errors.isEmpty()) throw new InvalidDataException(errors);
    }
}