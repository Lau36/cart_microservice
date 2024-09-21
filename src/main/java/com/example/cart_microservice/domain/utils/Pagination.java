package com.example.cart_microservice.domain.utils;

public class Pagination {
    private int page;
    private int size;
    private String sort;
    private SortDirection sortDirection;
    private String filter;

    public Pagination(int page, int size, String sort, SortDirection sortDirection, String filter) {
        this.page = page;
        this.size = size;
        this.sort =sort;
        this.sortDirection = sortDirection;
        this.filter = filter;
    }

    public String getSort() {
        return sort;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public String getFilter() {
        return filter;
    }
}
