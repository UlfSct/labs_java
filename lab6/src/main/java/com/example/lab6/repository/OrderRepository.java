package com.example.lab6.repository;

import com.example.lab6.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE LOWER(o.client) LIKE LOWER(CONCAT('%', :client, '%'))")
    Page<Order> findByClientContainingIgnoreCase(@Param("client") String client, Pageable pageable);
}