package com.example.lab6.repository;

import com.example.lab6.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("""
        SELECT b FROM Book b WHERE
        LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))
    """)
    Page<Book> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
}