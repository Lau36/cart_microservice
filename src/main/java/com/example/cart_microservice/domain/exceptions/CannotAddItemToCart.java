package com.example.cart_microservice.domain.exceptions;

public class CannotAddItemToCart extends RuntimeException {
    public CannotAddItemToCart(String message) {
        super(message);
    }
}
