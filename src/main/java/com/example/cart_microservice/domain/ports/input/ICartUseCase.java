package com.example.cart_microservice.domain.ports.input;

import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsInCartPaginationRequest;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsPaginatedWithPrice;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsWithNextSupplyDate;

public interface ICartUseCase {
    Cart addCart(Cart cart);
    String deleteCart(Long itemId);
    String buy();
    ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> getAllItemsInCart(ItemsInCartPaginationRequest pagination);
}
