package com.example.cart_microservice.infrastructure.adapters.input.controller;

import com.example.cart_microservice.aplication.services.CartService;
import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.infrastructure.adapters.input.controller.mapper.AddCartRequest;
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

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestParam Long itemId, @RequestParam Integer quantity) {
        Cart cart = addCartRequest.toCart(itemId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addCart(cart));
    }
    @DeleteMapping
    public ResponseEntity<String> deleteItemInCart(@RequestParam Long itemId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.deleteCart(itemId));
    }

    @PostMapping("/")
    public ResponseEntity<String> buyCart() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.buy());
    }
}
