package com.example.cart_microservice.domain.models;


import java.time.LocalDateTime;

public class Cart {
    private Long id;
    private Long userId;
    private Long itemId;
    private Integer quantity;
    private LocalDateTime updatedAt;
    private String status;
    private Boolean deleted;

    public Cart(Long id, Long userId, Long itemId, Integer quantity, LocalDateTime updatedAt, String status, Boolean deleted) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.updatedAt = updatedAt;
        this.status = status;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
