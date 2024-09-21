package com.example.cart_microservice.infrastructure.adapters.input.controller.mapper;

import com.example.cart_microservice.domain.models.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddCartRequest {
    Cart toCart(Long itemId, Integer quantity);
}
