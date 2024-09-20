package com.example.cart_microservice.infrastructure.configuration.feignclient;

import com.example.cart_microservice.infrastructure.adapters.input.controller.dto.response.ResponseNextSupplyDate;
import com.example.cart_microservice.infrastructure.configuration.feignclient.feignconfig.FeingConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "transaction", url = "http://localhost:6060/Supplies", configuration = FeingConfig.class)
public interface TransactionClient {
    @GetMapping
    ResponseEntity<ResponseNextSupplyDate> getNextSupply(@RequestParam int itemId);
}
