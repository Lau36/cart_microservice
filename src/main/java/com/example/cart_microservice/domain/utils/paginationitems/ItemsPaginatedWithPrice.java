package com.example.cart_microservice.domain.utils.paginationitems;


import java.math.BigDecimal;
import java.util.List;


public class ItemsPaginatedWithPrice<T> {
    private List<T> items;
    private BigDecimal totalPrice;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public ItemsPaginatedWithPrice(List<T> items, BigDecimal totalPrice, int currentPage, int totalPages, long totalElements) {
        this.items = items;
        this.totalPrice = totalPrice;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<T> getItems() {
        return items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
