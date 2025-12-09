package com.example.lab6.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "client", nullable = false, length = 250)
    private String client;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_books",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

    public Order(String client, List<Book> books) {
        this.client = client;
        this.books = books != null ? books : new ArrayList<>();
    }

    @Transient
    public List<HashMap<String, String>> getBooksWithAuthors() {
        List<HashMap<String, String>> result = new ArrayList<>();
        if (books != null) {
            for (Book book : books) {
                HashMap<String, String> item = new HashMap<>();
                item.put("id", book.getId().toString());
                item.put("title", book.getTitle() + " (" + book.getAuthorFullName() + ")");
                result.add(item);
            }
        }
        return result;
    }
}