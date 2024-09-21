package com.example.cart_microservice.domain.models;

import java.time.LocalDateTime;

public class ItemCart {
    private Long itemId;
    private Integer quantity;
    private LocalDateTime updateAt;
    private String status;

    public ItemCart(String status, Long itemId, LocalDateTime updateAt, Integer quantity) {
        this.status = status;
        this.itemId = itemId;
        this.updateAt = updateAt;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public String getStatus() {
        return status;
    }
}
