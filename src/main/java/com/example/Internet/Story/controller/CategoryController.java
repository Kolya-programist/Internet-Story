package com.example.Internet.Story.controller;

import com.example.Internet.Story.entity.Category;
import com.example.Internet.Story.dto.CategoryDto;
import com.example.Internet.Story.mapper.CategoryMapper;
import com.example.Internet.Story.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryDto> getAll(){
        return categoryService.getAllCategory()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable Long id){
        return categoryMapper.toDto(categoryService.getCategoryById(id));
    }

    @GetMapping("/id/increase")
    public List<CategoryDto> getAllCategoryByIdIncrease(){
        return categoryService.getAllCategoryByIdIncrease()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/id/decline")
    public List<CategoryDto> getAllCategoryByIdDecline(){
        return categoryService.getAllCategoryByIdDecline()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/name/{name}")
    public List<CategoryDto> findByName(@PathVariable String name){
        return categoryService.getCategoryByName(name)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/name/sort")
    public List <CategoryDto> getAllCategorySortedName(){
        return categoryService.getAllCategorySortedName()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CategoryDto post(@RequestBody CategoryDto categoryDto){
        Category category = categoryMapper.toEntity(categoryDto);
        Category save = categoryService.postCategory(category);
        return categoryMapper.toDto(save);
    }

    @PutMapping("/{id}")
    public CategoryDto put(@PathVariable Long id, @RequestBody CategoryDto categoryDto){
        Category category = categoryMapper.toEntity(categoryDto);
        Category save = categoryService.putCategory(id, category);
        return categoryMapper.toDto(save);
    }

    @DeleteMapping("/{id}")
    public CategoryDto delete(@PathVariable Long id){
        Category category = categoryService.getCategoryById(id);
        categoryService.deleteCategory(id);
        return categoryMapper.toDto(category);
    }
}
