package com.example.cart_microservice.infrastructure.configuration.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import com.example.cart_microservice.infrastructure.configuration.feignclient.feignconfig.FeingConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@FeignClient(name = "stock", url = "http://localhost:9090/Item", configuration = FeingConfig.class)
public interface StokClient {

    @GetMapping("/InStock ")
    ResponseEntity<Boolean> isInStock(@RequestParam int itemId, @RequestParam int quantity);

    @GetMapping("/Categories")
    ResponseEntity<List<Long>> getCategoriesByItemId(@RequestParam int itemId);

    @GetMapping("/Info")
    ResponseEntity<List<Long>> getItemInfo(@RequestParam int itemId);
}
