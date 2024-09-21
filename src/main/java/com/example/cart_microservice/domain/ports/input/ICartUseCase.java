package com.example.cart_microservice.domain.ports.input;

import com.example.cart_microservice.domain.models.Cart;

public interface ICartUseCase {
    Cart addCart(Cart cart);
    String deleteCart(Long itemId);
    String buy();
}
