package com.example.lab6.repository;

import com.example.lab6.model.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInfoRepository extends JpaRepository<BookInfo, Integer> {
    boolean existsByIsbn(String isbn);
    BookInfo findByBookId(Integer bookId);
}