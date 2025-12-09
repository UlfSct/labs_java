package com.example.lab6.request.author;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorSelectorRequest {
    private Integer id;
    private String fullName;
}
