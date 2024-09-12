package com.example.cart_microservice.infrastructure.adapters.output;

import com.example.cart_microservice.domain.ports.output.ICartPersistencePort;
import com.example.cart_microservice.utils.Constants;

public class CartPersitenceAdapter implements ICartPersistencePort {
    @Override
    public String saveCart() {
        return Constants.ADD_CART;
    }

    @Override
    public String deleteCart() {
        return Constants.DELETE_CART;
    }

    @Override
    public String buy() {
        return Constants.BUY;
    }
}
