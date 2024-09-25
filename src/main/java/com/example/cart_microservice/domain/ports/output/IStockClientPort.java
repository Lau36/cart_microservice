package com.example.cart_microservice.domain.ports.output;

import com.example.cart_microservice.domain.utils.paginationitems.ItemsInCartPaginationRequest;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsRequest;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsWithPrice;
import com.example.cart_microservice.domain.utils.paginationitems.PaginatedItemResponse;

import java.util.List;

public interface IStockClientPort {
    Boolean inStock(Long itemId, Integer quantity);
    List<Long> getCategoriesByItemId(Long itemId);
    PaginatedItemResponse getPaginatedItems(ItemsInCartPaginationRequest paginationRequest, ItemsRequest itemsRequest);
    List<ItemsWithPrice> getItemsWithPrice(ItemsRequest itemsRequest);
}
