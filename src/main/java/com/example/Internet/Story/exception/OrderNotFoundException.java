package com.example.Internet.Story.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id){
        super("Order by id " + id + " not found!");
    }
}
