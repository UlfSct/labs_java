package com.example.lab6.request.author;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorUpdateRequest {
    private String name;
    private String surname;
    private String lastname;
}
