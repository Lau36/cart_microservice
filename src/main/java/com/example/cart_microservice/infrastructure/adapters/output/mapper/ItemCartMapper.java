package com.example.cart_microservice.infrastructure.adapters.output.mapper;

import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.utils.ItemDTO;

import java.util.List;

public interface ItemCartMapper {
    List<ItemDTO> mapCartListToItemDTOList(List<Cart> cartList);
}
