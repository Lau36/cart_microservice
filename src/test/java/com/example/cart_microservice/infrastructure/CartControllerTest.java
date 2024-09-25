package com.example.cart_microservice.infrastructure;

import com.example.cart_microservice.aplication.services.CartService;
import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsInCartPaginationRequest;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsPaginatedWithPrice;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsWithNextSupplyDate;
import com.example.cart_microservice.infrastructure.adapters.input.controller.CartController;
import com.example.cart_microservice.infrastructure.adapters.input.controller.mapper.AddCartRequest;
import com.example.cart_microservice.infrastructure.utils.Status;
import com.example.cart_microservice.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CartControllerTest {

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
        cart = new Cart(1L, 1L, itemId, quantity, LocalDateTime.now(), Status.STANDBY.toString(), Boolean.FALSE);
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
                .andExpect(jsonPath("$.status").value(Status.STANDBY.toString()));

        verify(cartService, times(1)).addCart(any(Cart.class));
    }

    @Test
    void testDeleteCart() throws Exception {
        when(cartService.deleteCart(itemId)).thenReturn(Constants.DELETE_CART);

        mockMvc.perform(delete("/Cart")
                        .param("itemId", String.valueOf(itemId)))
                .andExpect(status().isOk())
                .andExpect(content().string(Constants.DELETE_CART));

        verify(cartService, times(1)).deleteCart(itemId);
    }

    @Test
    void testBuyCart() throws Exception {
        when(cartService.buy()).thenReturn(Constants.BUY);

        mockMvc.perform(post("/Cart/"))
                .andExpect(status().isOk())
                .andExpect(content().string(Constants.BUY));

        verify(cartService, times(1)).buy();
    }

    @Test
    public void testGetAllItemsInCart() throws Exception {
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

        when(cartService.getAllItemsInCart(any(ItemsInCartPaginationRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/Cart")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sortDirection", "ASC")
                        .param("filter", "CATEGORYNAME")
                        .param("filterName", "Electronicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.totalPrice").value(60))
                .andExpect(jsonPath("$.items[0].id").value(1))
                .andExpect(jsonPath("$.items[0].name").value("Item 1"))
                .andExpect(jsonPath("$.items[0].price").value(10))
                .andExpect(jsonPath("$.items[1].id").value(2))
                .andExpect(jsonPath("$.items[1].name").value("Item 2"))
                .andExpect(jsonPath("$.items[1].price").value(20));

        verify(cartService, times(1)).getAllItemsInCart(any(ItemsInCartPaginationRequest.class));
    }


}
