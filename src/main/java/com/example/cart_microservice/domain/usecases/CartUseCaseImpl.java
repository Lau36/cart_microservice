package com.example.cart_microservice.domain.usecases;

import com.example.cart_microservice.domain.exceptions.CannotAddItemToCart;
import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.ports.input.ICartUseCase;
import com.example.cart_microservice.domain.ports.output.ICartPersistencePort;
import com.example.cart_microservice.domain.ports.output.IStockClientPort;
import com.example.cart_microservice.domain.ports.output.ITransactionClientPort;
import com.example.cart_microservice.domain.ports.output.IUserId;
import com.example.cart_microservice.domain.utils.DomainConstans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartUseCaseImpl implements ICartUseCase {
    private final ICartPersistencePort cartPersistencePort;
    private final IStockClientPort stockClientPort;
    private final ITransactionClientPort transactionClientPort;
    private final IUserId userIdPort;

    public CartUseCaseImpl(ICartPersistencePort cartPersistencePort, IStockClientPort stockClientPort, ITransactionClientPort transactionClientPort, IUserId userIdPort) {
        this.cartPersistencePort = cartPersistencePort;
        this.stockClientPort = stockClientPort;
        this.transactionClientPort = transactionClientPort;
        this.userIdPort = userIdPort;
    }

    @Override
    public Cart addCart(Cart cart) {
        cartValidations(cart);
        return cartPersistencePort.saveCart(cart);
    }

    @Override
    public String deleteCart() {
        return cartPersistencePort.deleteCart();
    }

    @Override
    public String buy() {
        return cartPersistencePort.buy();
    }
    
    private void cartValidations(Cart cart) {
        Long userId = userIdPort.getUserId();


        if(canAddItemToCart(userId, cart.getIdItem()) == Boolean.FALSE){
            throw new CannotAddItemToCart(DomainConstans.CANNOT_ADD_ITEM_TO_CART_MESSAGE);
        }

    }

    private Map<Long, Integer> getCategoryCountsForCart(Long userId) {
        List<Cart> carts = cartPersistencePort.getAllItemsByUserId(userId);
        Map<Long, Integer> categoryCounts = new HashMap<>();

        for(Cart cart : carts) {
            List<Long> categoriesByItem = stockClientPort.getCategoriesByItemId(cart.getIdItem());
            for (Long categoryId : categoriesByItem) {
                categoryCounts.put(categoryId, categoryCounts.getOrDefault(categoryId, 0) + 1);
            }
        }
        return categoryCounts;
    }

    private boolean canAddItemToCart(Long userId, Long itemId) {
        Map<Long, Integer> categoryCounts = getCategoryCountsForCart(userId);
        List<Long> newItemCategories = stockClientPort.getCategoriesByItemId(itemId);

        for(Long newCategoryId : newItemCategories) {
            if(categoryCounts.getOrDefault(newCategoryId, 0) > 3) {
                return false;
            }
        }
        return true;
    }
}
