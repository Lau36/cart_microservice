package com.example.cart_microservice.infrastructure.adapters;

import com.example.cart_microservice.domain.ports.output.ITransactionClientPort;
import com.example.cart_microservice.infrastructure.adapters.input.controller.dto.response.ResponseNextSupplyDate;
import com.example.cart_microservice.infrastructure.configuration.feignclient.TransactionClient;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class TransactionClientAdapter implements ITransactionClientPort {
    private final TransactionClient transactionClient;


    public LocalDateTime nextSupplyDate(Long itemId) {
        ResponseNextSupplyDate response = transactionClient.getNextSupply(itemId.intValue()).getBody();
        return response.getNextSupplyDate();
    }
}
