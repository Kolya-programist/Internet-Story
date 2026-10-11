package com.example.Internet.Story.service;

import com.example.Internet.Story.entity.Category;
import com.example.Internet.Story.exception.CategoryNotFoundException;
import com.example.Internet.Story.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
//Найти все категории товаров
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

//Найти категорию товаров по id
    public Category getCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
//Найти все категории по возрастанию Id
    public List<Category> getAllCategoryByIdIncrease(){
        return categoryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Category::getId))
                .collect(Collectors.toList());
    }
//Найти все категории по убыванию Id
    public List<Category> getAllCategoryByIdDecline(){
        return categoryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Category::getId).reversed())
                .collect(Collectors.toList());
    }
//Найти категории по имени
    public List<Category> getCategoryByName(String name){
        return categoryRepository.findByName(name);
    }

//Сортировать сатегории по алфавитному порядку
    public List<Category> getAllCategorySortedName(){
        return categoryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Category::getName))
                .collect(Collectors.toList());
    }
//Загрузить категорию товара
    @Transactional
    public Category postCategory(Category category){
        Category saved = categoryRepository.save(category);
        log.info("Категория {} загружена", saved.getId());
        return saved;
    }

//Обновить категорию товара
    @Transactional
public Category putCategory(Long id, Category category){
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        existing.setName(category.getName());
        log.info("Категория {} обновлена", existing.getId());
        return categoryRepository.save(existing);
    }

//Удалить категорию товара
    @Transactional
    public void deleteCategory(Long id){
        categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.deleteById(id);
        log.info("Категория {} удалена", id);
    }
}
