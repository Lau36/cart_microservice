package com.example.cart_microservice.domain.ports.output;

import com.example.cart_microservice.domain.models.Cart;

import java.util.List;

public interface ICartPersistencePort {
    Cart saveCart(Cart cart);
    List<Cart> getAllItemsByUserId(Long userId);
    String deleteCart();
    String buy();
}
