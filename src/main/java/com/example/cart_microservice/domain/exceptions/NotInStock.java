package com.example.cart_microservice.domain.exceptions;

public class NotInStock extends RuntimeException {
    public NotInStock(String message) {
        super(message);
    }
}
