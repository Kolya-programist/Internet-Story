package com.example.Internet.Story.repository;

import com.example.Internet.Story.entity.Order;
import com.example.Internet.Story.entity.OrderItem;
import com.example.Internet.Story.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder (Order order);
    List<OrderItem> findByProduct (Product product);
    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.product p JOIN FETCH p.category WHERE oi.order = :order")
    List<OrderItem> findByOrderWithProduct(@Param("order") Order order);
}
