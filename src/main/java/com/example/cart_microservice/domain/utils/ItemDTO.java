package com.example.cart_microservice.domain.utils;

import java.util.List;

public class ItemDTO {
    private Long userId;
    private List<Items> items;

    public Long getUserId() {
        return userId;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
