package com.example.Internet.Story.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Long id){
        super("Category by id " + id + " not found");
    }
}
