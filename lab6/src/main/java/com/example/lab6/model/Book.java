package com.example.lab6.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "year", nullable = false)
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnore
    private Author author;

    @Column(name = "author_id", insertable = false, updatable = false, nullable = false)
    private Integer authorId;

    public Book(String title, Integer year, Author author) {
        this.title = title;
        this.year = year;
        this.author = author;
        this.authorId = author != null ? author.getId() : null;
    }

    @Transient
    public String getAuthorFullName() {
        return author != null ? author.getFullName() : "";
    }

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private BookInfo bookInfo;
}