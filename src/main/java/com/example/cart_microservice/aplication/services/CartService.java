package com.example.cart_microservice.aplication.services;

import com.example.cart_microservice.domain.ports.input.ICartUseCase;

public class CartService implements ICartUseCase {
    private final ICartUseCase cartUseCase;

    public CartService(ICartUseCase cartUseCase) {
        this.cartUseCase = cartUseCase;
    }

    @Override
    public String addCart() {
        return cartUseCase.addCart();
    }

    @Override
    public String deleteCart() {
        return cartUseCase.deleteCart();
    }

    @Override
    public String buy() {
        return cartUseCase.buy();
    }
}
