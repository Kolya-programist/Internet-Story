package com.example.Internet.Story.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id){
        super("Customer by id " + id + " not found");
    }

    public CustomerNotFoundException(String email){
        super("Customer by email " + email + " not found" );
    }
}
