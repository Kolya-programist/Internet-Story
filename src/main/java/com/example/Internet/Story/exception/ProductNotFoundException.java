package com.example.Internet.Story.exception;

public class ProductNotFoundException extends RuntimeException {
    public  ProductNotFoundException(Long id){
        super("Product by id " + id + " not found!");
    }
}
