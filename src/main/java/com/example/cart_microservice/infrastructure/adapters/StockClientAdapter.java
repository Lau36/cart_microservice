package com.example.cart_microservice.infrastructure.adapters;

import com.example.cart_microservice.domain.ports.output.IStockClientPort;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsInCartPaginationRequest;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsRequest;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsWithPrice;
import com.example.cart_microservice.domain.utils.paginationitems.PaginatedItemResponse;
import com.example.cart_microservice.infrastructure.configuration.feignclient.StokClient;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class StockClientAdapter implements IStockClientPort {
    private final StokClient stokClient;

    public Boolean inStock(Long itemId, Integer quantity) {
        return stokClient.isInStock(itemId.intValue(), quantity).getBody();
    }

    @Override
    public List<Long> getCategoriesByItemId(Long itemId) {
        return stokClient.getCategoriesByItemId(itemId.intValue()).getBody();
    }

    @Override
    public PaginatedItemResponse getPaginatedItems(ItemsInCartPaginationRequest paginationRequest, ItemsRequest itemsRequest) {
        return stokClient.getItemsPaginated(
                paginationRequest.getPage(),
                paginationRequest.getSize(),
                paginationRequest.getSortDirection().toString(),
                paginationRequest.getFilter().toString(),
                paginationRequest.getFilterName(),
                itemsRequest).getBody();
    }

    @Override
    public List<ItemsWithPrice> getItemsWithPrice(ItemsRequest itemsRequest) {
        return stokClient.getItemsWithPrice(itemsRequest).getBody();
    }
}
