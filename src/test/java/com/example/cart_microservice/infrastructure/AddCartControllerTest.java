package com.example.cart_microservice.infrastructure;

import com.example.cart_microservice.aplication.services.CartService;
import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.infrastructure.adapters.input.controller.CartController;
import com.example.cart_microservice.infrastructure.adapters.input.controller.mapper.AddCartRequest;
import com.example.cart_microservice.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AddCartControllerTest {

    @Mock
    CartService cartService;

    @Mock
    AddCartRequest addCartRequest;

    private MockMvc mockMvc;

    @InjectMocks
    CartController cartController;

    private Cart cart;
    private final Long itemId = 1L;
    private final Integer quantity = 3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart(1L, 1L, itemId, quantity, LocalDateTime.now(), "ACTIVE", Boolean.FALSE);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    void testCreateCart() throws Exception {
        when(addCartRequest.toCart(itemId, quantity)).thenReturn(cart);
        when(cartService.addCart(any(Cart.class))).thenReturn(cart);

        mockMvc.perform(post("/Cart")
                        .param("itemId", String.valueOf(itemId))
                        .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(itemId))
                .andExpect(jsonPath("$.quantity").value(quantity))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(cartService, times(1)).addCart(any(Cart.class));
    }

    @Test
    void testDeleteCart() throws Exception {
        when(cartService.deleteCart()).thenReturn(Constants.DELETE_CART);

        mockMvc.perform(delete("/Cart"))
                .andExpect(status().isOk())
                .andExpect(content().string(Constants.DELETE_CART));

        verify(cartService, times(1)).deleteCart();
    }

    @Test
    void testBuyCart() throws Exception {
        when(cartService.buy()).thenReturn(Constants.BUY);

        mockMvc.perform(post("/Cart/"))
                .andExpect(status().isOk())
                .andExpect(content().string(Constants.BUY));

        verify(cartService, times(1)).buy();
    }

}
