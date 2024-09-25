package com.example.cart_microservice.domain.utils.paginationitems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsWithPrice {
    private Long id;
    private BigDecimal price;
}
