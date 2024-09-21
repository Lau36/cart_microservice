package com.example.cart_microservice.infrastructure.adapters.input.controller.dto.response;

import java.util.List;

public class ListCategoriesResponse {
    private List<Long> id_categories;

    public ListCategoriesResponse() {
    }

    public ListCategoriesResponse(List<Long> idCategories) {
        id_categories = idCategories;
    }

    public List<Long> getId_categories() {
        return id_categories;
    }
}
