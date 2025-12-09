package com.example.lab6.repository;

import com.example.lab6.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query("""
        SELECT a FROM Author a WHERE
        LOWER(a.surname) LIKE LOWER(CONCAT('%', :search, '%')) OR
        LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
        (a.lastname IS NOT NULL AND LOWER(a.lastname) LIKE LOWER(CONCAT('%', :search, '%'))) OR
        LOWER(CONCAT(a.surname, ' ', a.name)) LIKE LOWER(CONCAT('%', :search, '%')) OR
        (a.lastname IS NOT NULL AND LOWER(CONCAT(a.surname, ' ', a.name, ' ', a.lastname)) LIKE LOWER(CONCAT('%', :search, '%'))) OR
        (a.lastname IS NOT NULL AND LOWER(CONCAT(a.name, ' ', a.surname, ' ', a.lastname)) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    Page<Author> findByFullNameContainingIgnoreCase(@Param("search") String query, Pageable pageable);
}


