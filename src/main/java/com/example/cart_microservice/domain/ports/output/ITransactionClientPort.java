package com.example.cart_microservice.domain.ports.output;

import java.time.LocalDateTime;

public interface ITransactionClientPort {
    LocalDateTime nextSupplyDate(Long itemId);
}
