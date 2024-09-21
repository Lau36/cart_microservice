package com.example.cart_microservice.domain.ports.output;

import java.util.List;

public interface IStockClientPort {
    Boolean inStock(Long itemId, Integer quantity);
    List<Long> getCategoriesByItemId(Long itemId);
}
