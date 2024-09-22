package com.example.cart_microservice.infrastructure.adapters.output.mapper;

import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.utils.ItemDTO;
import com.example.cart_microservice.domain.utils.Items;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemCartMapperImpl implements ItemCartMapper {

    @Override
    public List<ItemDTO> mapCartListToItemDTOList(List<Cart> cartList) {
        Map<Long, List<Items>> userItemsMap = cartList.stream()
                .collect(Collectors.groupingBy(
                        Cart::getUserId,
                        Collectors.mapping(cart -> {
                            Items item = new Items();
                            item.setItemId(cart.getItemId());
                            item.setQuantity(cart.getQuantity());
                            return item;
                        }, Collectors.toList())
                ));

        return userItemsMap.entrySet().stream().map(entry -> {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setUserId(entry.getKey());
            itemDTO.setItems(entry.getValue());
            return itemDTO;
        }).collect(Collectors.toList());
    }
}
