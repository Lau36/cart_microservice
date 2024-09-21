package com.example.cart_microservice.infrastructure.adapters.input.controller;

import com.example.cart_microservice.aplication.services.CartService;
import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.infrastructure.adapters.input.controller.mapper.AddCartRequest;
import com.example.cart_microservice.infrastructure.adapters.input.controller.utils.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final AddCartRequest addCartRequest;

    @Operation(
            summary = SwaggerConstants.ADD_CART,
            description = SwaggerConstants.ADD_CART_DESCRIPTION,
            security = @SecurityRequirement(name = SwaggerConstants.BEARER_TOKEN)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SwaggerConstants.ADDED_CART),
            @ApiResponse(responseCode = "400", description = SwaggerConstants.BAD_REQUEST),
            @ApiResponse(responseCode = "503", description = SwaggerConstants.SERVICE_DOWN)
    })
    @PostMapping
    public ResponseEntity<Cart> addCart(@RequestParam Long itemId, @RequestParam Integer quantity) {
        Cart cart = addCartRequest.toCart(itemId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addCart(cart));
    }

    @Operation(
            summary = SwaggerConstants.DELETE_ITEM_CART,
            description = SwaggerConstants.DELETE_ITEM_CART_DESCRIPTION,
            security = @SecurityRequirement(name = SwaggerConstants.BEARER_TOKEN)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerConstants.DELETED_ITEM_CART),
            @ApiResponse(responseCode = "404", description = SwaggerConstants.NOT_FOUND),
            @ApiResponse(responseCode = "503", description = SwaggerConstants.SERVICE_DOWN)
    })
    @DeleteMapping
    public ResponseEntity<String> deleteItemInCart(@RequestParam Long itemId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.deleteCart(itemId));
    }

    @Operation(
            summary = SwaggerConstants.BUY_CART,
            description = SwaggerConstants.BUY_CART_DESCRIPTION,
            security = @SecurityRequirement(name = SwaggerConstants.BEARER_TOKEN)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerConstants.PURCHASED_CART),
            @ApiResponse(responseCode = "400", description = SwaggerConstants.BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = SwaggerConstants.NOT_FOUND),
            @ApiResponse(responseCode = "503", description = SwaggerConstants.SERVICE_DOWN)
    })
    @PostMapping("/")
    public ResponseEntity<String> buyCart() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.buy());
    }
}
