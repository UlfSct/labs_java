package com.example.lab6.validator;

import com.example.lab6.errors.InvalidDataException;
import com.example.lab6.request.author.AuthorCreateRequest;
import com.example.lab6.request.author.AuthorUpdateRequest;

import java.util.HashMap;

public class AuthorValidator {
    private HashMap<String, String> getAuthorDefaultErrors(String name, String surname, String lastname) {
        HashMap<String, String> errors = new HashMap<>();

        if (name == null) errors.put("name", "Обязательное поле");
        else if (name.length() < 2) errors.put("name", "Имя должно содержать минимум 2 буквы");
        else if (name.length() > 100) errors.put("name", "Имя не может содержать больше 100 букв");

        if (surname == null) errors.put("surname", "Обязательное поле");
        else if (surname.length() < 2) errors.put("surname", "Фамилия должна содержать минимум 2 буквы");
        else if (surname.length() > 100) errors.put("name", "Фамилия не может содержать больше 100 букв");

        if (lastname != null)
        {
            if (lastname.length() < 2)
            {
                errors.put("lastname", "Отчество должно содержать минимум 2 буквы или отсутствовать вовсе");
            }
            else if (lastname.length() > 100)
            {
                errors.put("lastname", "Отчество должно содержать максимум 100 букв или отсутствовать вовсе");
            }

        }
        return errors;
    }

    public void validate(AuthorCreateRequest data)
    {
        HashMap<String, String> errors = getAuthorDefaultErrors(data.getName(), data.getSurname(), data.getLastname());
        if (!errors.isEmpty()) throw new InvalidDataException(errors);
    }

    public void validate(AuthorUpdateRequest data)
    {
        HashMap<String, String> errors = getAuthorDefaultErrors(data.getName(), data.getSurname(), data.getLastname());
        if (!errors.isEmpty()) throw new InvalidDataException(errors);
    }
}
