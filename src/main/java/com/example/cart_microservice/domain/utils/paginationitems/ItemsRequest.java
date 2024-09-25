package com.example.cart_microservice.domain.utils.paginationitems;

import java.util.List;

public class ItemsRequest {
    private List<Integer> itemsId;

    public ItemsRequest(List<Integer> itemsId) {
        this.itemsId = itemsId;
    }

    public List<Integer> getItemsId() {
        return itemsId;
    }
}
