package com.example.lab6.request.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {
    private String title;
    private Integer year;
    private Integer authorId;
    private String isbn;
}