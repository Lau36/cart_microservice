package com.example.cart_microservice.domain.utils.paginationitems;

import java.util.List;

public class PaginatedItemResponse {
    private List<ItemResponseDTO> items;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public PaginatedItemResponse(List<ItemResponseDTO> items, int currentPage, int totalPages, long totalElements) {
        this.items = items;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<ItemResponseDTO> getItems() {
        return items;
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
}
