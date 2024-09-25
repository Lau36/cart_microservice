package com.example.cart_microservice.application;

import com.example.cart_microservice.aplication.services.CartService;
import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.ports.input.ICartUseCase;
import com.example.cart_microservice.domain.utils.Filter;
import com.example.cart_microservice.domain.utils.SortDirection;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsInCartPaginationRequest;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsPaginatedWithPrice;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsWithNextSupplyDate;
import com.example.cart_microservice.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartServiceTest {
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

    @Test
    public void testGetAllItemsInCart() {
        // Datos simulados que serán retornados por el mock de cartUseCase
        List<ItemsWithNextSupplyDate> items = List.of(
                new ItemsWithNextSupplyDate(
                        1L,
                        "Item 1",
                        "Description 1",
                        null,
                        5,
                        true,
                        null,
                        BigDecimal.TEN,
                        List.of(),
                        null),
                new ItemsWithNextSupplyDate(
                        2L,
                        "Item 2",
                        "Description 2",
                        null,
                        0,
                        false,
                        LocalDateTime.now(),
                        BigDecimal.valueOf(20),
                        List.of(),
                        null)
        );
        ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> response = new ItemsPaginatedWithPrice<>(items,
                BigDecimal.valueOf(60), 1, 1, 2);

        when(service.getAllItemsInCart(any(ItemsInCartPaginationRequest.class))).thenReturn(response);

        ItemsInCartPaginationRequest paginationRequest = new ItemsInCartPaginationRequest(0, 10, SortDirection.ASC, Filter.CATEGORYNAME, "Electronicos");

        ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> actualResponse = service.getAllItemsInCart(paginationRequest);

        verify(useCase, times(1)).getAllItemsInCart(paginationRequest);

        assertEquals(response, actualResponse);
    }
}
