package com.example.cart_microservice.aplication.services;

import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.ports.input.ICartUseCase;
import com.example.cart_microservice.domain.utils.Paginated;
import com.example.cart_microservice.domain.utils.Pagination;

public class CartService implements ICartUseCase {
    private final ICartUseCase cartUseCase;

    public CartService(ICartUseCase cartUseCase) {
        this.cartUseCase = cartUseCase;
    }

    @Override
    public Cart addCart(Cart cart) {
        return cartUseCase.addCart(cart);
    }

    @Override
    public String deleteCart(Long itemId) {
        return cartUseCase.deleteCart(itemId);
    }

    @Override
    public String buy() {
        return cartUseCase.buy();
    }

    @Override
    public Paginated<Cart> getAllImtensInCart(Pagination pagination) {
        return cartUseCase.getAllImtensInCart(pagination);
    }
}
