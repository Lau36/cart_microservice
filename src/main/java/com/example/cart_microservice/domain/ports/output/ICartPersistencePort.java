package com.example.cart_microservice.domain.ports.output;

public interface ICartPersistencePort {
    String saveCart();
    String deleteCart();
    String buy();
}
