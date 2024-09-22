package com.example.cart_microservice.domain.utils;


public class Brand {
    private final String name;
    private final String description;

    public Brand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
