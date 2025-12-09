package com.example.lab6.errors;

import lombok.Data;

import java.util.*;

@Data
public class ServerError {
    private Map<String, String> errors;

    public ServerError(String detail) {
        this.errors = new HashMap<>();
        this.errors.put("detail", detail);
    }

    public ServerError(Map<String, String> fieldErrors) {
        this.errors = new HashMap<>(fieldErrors);
    }

    public ServerError(String field, String errorMessage) {
        this.errors = new HashMap<>();
        this.errors.put(field, errorMessage);
    }
}