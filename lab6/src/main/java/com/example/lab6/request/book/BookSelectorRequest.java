package com.example.lab6.request.book;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookSelectorRequest {
    private Integer id;
    private String titleWithAuthor;
}