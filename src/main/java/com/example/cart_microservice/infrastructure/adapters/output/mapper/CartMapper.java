package com.example.cart_microservice.infrastructure.adapters.output.mapper;

import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.infrastructure.adapters.output.entity.CartEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toCart(CartEntity cartEntity);
    List<Cart> toCarts(List<CartEntity> cartEntities);
}
