package com.example.Internet.Story.exception;

public class ProductsEmptyException extends RuntimeException {
    public ProductsEmptyException() {
        super("Products not found!");
    }
}
