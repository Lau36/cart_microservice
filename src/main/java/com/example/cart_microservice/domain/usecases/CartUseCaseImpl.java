package com.example.cart_microservice.domain.usecases;

import com.example.cart_microservice.domain.ports.input.ICartUseCase;
import com.example.cart_microservice.domain.ports.output.ICartPersistencePort;

public class CartUseCaseImpl implements ICartUseCase {
    private final ICartPersistencePort cartPersistencePort;

    public CartUseCaseImpl(ICartPersistencePort cartPersistencePort) {
        this.cartPersistencePort = cartPersistencePort;
    }

    @Override
    public String addCart() {
        return cartPersistencePort.saveCart();
    }

    @Override
    public String deleteCart() {
        return cartPersistencePort.deleteCart();
    }

    @Override
    public String buy() {
        return cartPersistencePort.buy();
    }
}
