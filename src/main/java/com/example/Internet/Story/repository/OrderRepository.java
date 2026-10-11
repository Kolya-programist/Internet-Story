package com.example.Internet.Story.repository;

import com.example.Internet.Story.entity.Customer;
import com.example.Internet.Story.entity.Order;
import com.example.Internet.Story.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
    List<Order> findByStatus (OrderStatus status);
}
