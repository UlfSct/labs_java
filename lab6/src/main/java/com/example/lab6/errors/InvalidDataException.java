package com.example.lab6.errors;

import lombok.Getter;
import java.util.Map;

@Getter
public class InvalidDataException extends RuntimeException {
    private final Map<String, String> fieldErrors;

    public InvalidDataException(Map<String, String> fieldErrors) {
        super("Неокрректные данные");
        this.fieldErrors = fieldErrors;
    }

    public InvalidDataException(String field, String errorMessage) {
        super("Неокрректные данные");
        this.fieldErrors = Map.of(field, errorMessage);
    }
}