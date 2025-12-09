package com.example.booklibrary.exception;

public class InvalidQueryPathException extends RuntimeException
{
    public InvalidQueryPathException()
    {
        super("Некорректный путь запроса");
    }
}