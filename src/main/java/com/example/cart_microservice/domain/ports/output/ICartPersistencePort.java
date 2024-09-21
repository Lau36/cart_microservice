package com.example.cart_microservice.domain.ports.output;

import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.models.ItemCart;

import java.util.List;
import java.util.Optional;

public interface ICartPersistencePort {
    Cart saveCart(Cart cart);
    List<ItemCart> getAllItemsByUserId(Long userId);
    Optional<Cart> findItemCartByUserIdAndItemId(Long itemId, Long userId);
    Cart updateItemInCart(Cart cart);
    String deleteCart();
    String buy();
}
