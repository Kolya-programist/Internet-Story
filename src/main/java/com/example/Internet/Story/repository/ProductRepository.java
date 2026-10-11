package com.example.Internet.Story.repository;

import com.example.Internet.Story.entity.Category;
import com.example.Internet.Story.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName (String name);
    List<Product> findByCategory (Category category);
}
