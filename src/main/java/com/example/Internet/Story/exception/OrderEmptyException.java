package com.example.Internet.Story.exception;

public class OrderEmptyException extends RuntimeException{
    public OrderEmptyException(){
        super("Orders not found!");
    }
}
