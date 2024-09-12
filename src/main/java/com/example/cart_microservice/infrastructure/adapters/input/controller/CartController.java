package com.example.cart_microservice.infrastructure.adapters.input.controller;

import com.example.cart_microservice.aplication.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> createCart() {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addCart());
    }
    @DeleteMapping
    public ResponseEntity<String> deleteart() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.deleteCart());
    }
    @PostMapping("/")
    public ResponseEntity<String> buyCart() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.buy());
    }
}
