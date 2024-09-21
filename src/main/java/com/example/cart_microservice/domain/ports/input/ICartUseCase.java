package com.example.cart_microservice.domain.ports.input;

import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.utils.Paginated;
import com.example.cart_microservice.domain.utils.Pagination;

public interface ICartUseCase {
    Cart addCart(Cart cart);
    String deleteCart(Long itemId);
    String buy();
    Paginated<Cart> getAllImtensInCart(Pagination pagination);
}
