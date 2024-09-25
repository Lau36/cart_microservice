package com.example.cart_microservice.domain.utils.paginationitems;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ItemsWithNextSupplyDate {
    private Long id;
    private String name;
    private String description;
    private Integer quantityInCart;
    private Integer quantityInStock;
    private Boolean areStock;
    private LocalDateTime nextSupplyDate;
    private BigDecimal price;
    private List<NameIdDTO> categories;
    private NameIdDTO brand;

    public ItemsWithNextSupplyDate(Long id,
                                   String name,
                                   String description,
                                   Integer quantityInCart,
                                   Integer quantityInStock,
                                   Boolean areStock,
                                   LocalDateTime nextSupplyDate,
                                   BigDecimal price,
                                   List<NameIdDTO> categories,
                                   NameIdDTO brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantityInCart = quantityInCart;
        this.quantityInStock = quantityInStock;
        this.areStock = areStock;
        this.nextSupplyDate = nextSupplyDate;
        this.price = price;
        this.categories = categories;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getAreStock() {
        return areStock;
    }

    public LocalDateTime getNextSupplyDate() {
        return nextSupplyDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<NameIdDTO> getCategories() {
        return categories;
    }

    public NameIdDTO getBrand() {
        return brand;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public Integer getQuantityInCart() {
        return quantityInCart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantityInCart(Integer quantityInCart) {
        this.quantityInCart = quantityInCart;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public void setAreStock(Boolean areStock) {
        this.areStock = areStock;
    }

    public void setNextSupplyDate(LocalDateTime nextSupplyDate) {
        this.nextSupplyDate = nextSupplyDate;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategories(List<NameIdDTO> categories) {
        this.categories = categories;
    }

    public void setBrand(NameIdDTO brand) {
        this.brand = brand;
    }
}
