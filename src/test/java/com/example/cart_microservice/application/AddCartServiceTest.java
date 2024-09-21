package com.example.cart_microservice.application;

import com.example.cart_microservice.aplication.services.CartService;
import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.ports.input.ICartUseCase;
import com.example.cart_microservice.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddCartServiceTest {
    @Mock
    ICartUseCase useCase;

    @InjectMocks
    CartService service;

    private Cart cart;
    private Long itemId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemId = 1L;
        cart = new Cart(1L, 1L, itemId, 2, LocalDateTime.now(), "ACTIVE", Boolean.FALSE);
    }

    @Test
    void testAddCart(){
        when(service.addCart(cart)).thenReturn(cart);
        Cart result = service.addCart(cart);
        assertEquals(cart, result);
        verify(useCase, times(1)).addCart(cart);
    }

    @Test
    void testDeleteCart(){
        when(service.deleteCart(itemId)).thenReturn(Constants.DELETE_CART);
        String result = service.deleteCart(itemId);
        assertEquals(Constants.DELETE_CART, result);
        verify(useCase, times(1)).deleteCart(itemId);
    }

    @Test
    void testBuyCart(){
        when(service.buy()).thenReturn(Constants.BUY);
        String result = service.buy();
        assertEquals(Constants.BUY, result);
        verify(useCase, times(1)).buy();
    }
}
