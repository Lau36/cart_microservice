package com.example.cart_microservice.domain.ports.output;

import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.models.ItemCart;
import com.example.cart_microservice.domain.utils.Paginated;
import com.example.cart_microservice.domain.utils.Pagination;

import java.util.List;
import java.util.Optional;

public interface ICartPersistencePort {
    Cart saveCart(Cart cart);
    List<ItemCart> getAllItemsByUserId(Long userId);
    Optional<Cart> findItemCartByUserIdAndItemId(Long itemId, Long userId);
    Cart updateItemInCart(Cart cart);
    String deleteCart(Long itemId, Long userId);
    Paginated<Cart> getAllImtensInCart(Pagination pagination, Long userId);
    String buy();
}
