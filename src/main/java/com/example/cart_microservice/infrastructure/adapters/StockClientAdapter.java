package com.example.cart_microservice.infrastructure.adapters;

import com.example.cart_microservice.domain.ports.output.IStockClientPort;
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
}
