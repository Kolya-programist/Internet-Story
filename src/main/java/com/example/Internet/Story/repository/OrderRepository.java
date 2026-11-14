package com.example.Internet.Story.repository;

import com.example.Internet.Story.entity.Customer;
import com.example.Internet.Story.entity.Order;
import com.example.Internet.Story.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
    List<Order> findByStatus (OrderStatus status);
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.orderItems oi " +
            "JOIN FETCH oi.product p " +
            "JOIN FETCH p.category " +
            "JOIN FETCH o.customer " +
            "WHERE o.id = :id")
    Optional<Order> findByIdWithOrderItems(@Param("id") Long id);
}
