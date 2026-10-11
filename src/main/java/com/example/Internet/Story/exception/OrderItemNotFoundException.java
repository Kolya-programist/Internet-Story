package com.example.Internet.Story.exception;

public class OrderItemNotFoundException extends  RuntimeException{
    public OrderItemNotFoundException(Long id){
        super("OrderItem by id " + id + " not found");
    }
}
