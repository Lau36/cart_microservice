package com.example.cart_microservice.infrastructure.configuration.feignclient;

import com.example.cart_microservice.domain.utils.paginationitems.ItemsRequest;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsWithPrice;
import com.example.cart_microservice.domain.utils.paginationitems.PaginatedItemResponse;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import com.example.cart_microservice.infrastructure.configuration.feignclient.feignconfig.FeingConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@FeignClient(name = "stock", url = "http://localhost:9090/Item", configuration = FeingConfig.class)
public interface StokClient{

    @GetMapping("/InStock ")
    ResponseEntity<Boolean> isInStock(@RequestParam int itemId, @RequestParam int quantity);

    @GetMapping("/Categories")
    ResponseEntity<List<Long>> getCategoriesByItemId(@RequestParam int itemId);

    @GetMapping("/Info")
    ResponseEntity<List<Long>> getItemInfo(@RequestParam int itemId);

    @GetMapping("/ItemsPaginated/")
    ResponseEntity<PaginatedItemResponse> getItemsPaginated(@RequestParam(defaultValue = "0")@Min(0) int page,
                                                            @RequestParam(defaultValue = "0")@Min(1)  int size,
                                                            @RequestParam(defaultValue = "asc")  String sortDirection,
                                                            @RequestParam  String filter,
                                                            @RequestParam String filterName,
                                                            @RequestBody ItemsRequest itemsRequest);

    @GetMapping("/Prices")
    ResponseEntity<List<ItemsWithPrice>> getItemsWithPrice(@RequestBody ItemsRequest itemsRequest);
}
