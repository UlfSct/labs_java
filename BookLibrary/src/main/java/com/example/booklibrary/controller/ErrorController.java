package com.example.booklibrary.controller;

import com.example.booklibrary.exception.InvalidQueryPathException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController
{
    @GetMapping("/error")
    public String error() {
        throw new InvalidQueryPathException();
    }
}
