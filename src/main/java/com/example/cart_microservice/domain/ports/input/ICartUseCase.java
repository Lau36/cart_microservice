package com.example.cart_microservice.domain.ports.input;

public interface ICartUseCase {
    String addCart();
    String deleteCart();
    String buy();
}
