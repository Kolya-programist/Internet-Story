package com.example.Internet.Story.repository;

import com.example.Internet.Story.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName (String name);
}
