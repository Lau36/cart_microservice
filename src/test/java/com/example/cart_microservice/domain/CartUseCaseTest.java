package com.example.cart_microservice.domain;

import com.example.cart_microservice.domain.exceptions.NotFoundException;
import com.example.cart_microservice.domain.exceptions.NotInStock;
import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.models.ItemCart;
import com.example.cart_microservice.domain.ports.output.ICartPersistencePort;
import com.example.cart_microservice.domain.ports.output.IStockClientPort;
import com.example.cart_microservice.domain.ports.output.ITransactionClientPort;
import com.example.cart_microservice.domain.ports.output.IUserId;
import com.example.cart_microservice.domain.usecases.CartUseCaseImpl;
import com.example.cart_microservice.domain.utils.DomainConstans;
import com.example.cart_microservice.domain.utils.Filter;
import com.example.cart_microservice.domain.utils.SortDirection;
import com.example.cart_microservice.domain.utils.paginationitems.*;
import com.example.cart_microservice.infrastructure.adapters.input.controller.dto.request.PaginationRequest;
import com.example.cart_microservice.infrastructure.utils.Status;
import com.example.cart_microservice.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartUseCaseTest {
    @Mock
    private ICartPersistencePort cartPersistencePort;

    @Mock
    private IStockClientPort stockClientPort;

    @Mock
    private ITransactionClientPort transactionClientPort;

    @Mock
    private IUserId userIdPort;

    @InjectMocks
    private CartUseCaseImpl cartUseCaseImpl;

    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart(1L, 1L, 1L, 2, LocalDateTime.now(), "ACTIVE", Boolean.FALSE);
    }

    @Test
    void testAddCart_CartExists() {
        Long userId = 1L;
        when(userIdPort.getUserId()).thenReturn(userId);
        when(cartPersistencePort.findItemCartByUserIdAndItemId(cart.getItemId(), userId)).thenReturn(Optional.of(cart));
        when(stockClientPort.inStock(cart.getItemId(), cart.getQuantity())).thenReturn(true);
        when(cartPersistencePort.updateItemInCart(cart)).thenReturn(cart);

        Cart result = cartUseCaseImpl.addCart(cart);

        assertEquals(cart.getQuantity(), result.getQuantity());
        verify(cartPersistencePort).updateItemInCart(cart);
    }
    @Test
    void testAddCart_WhenCartWasElimitated() {
        Long userId = 1L;
        Cart cartNew = new Cart(1L, 1L, 1L, 2, LocalDateTime.now(), "ACTIVE", Boolean.TRUE);
        when(userIdPort.getUserId()).thenReturn(userId);
        when(cartPersistencePort.findItemCartByUserIdAndItemId(cartNew.getItemId(), userId)).thenReturn(Optional.of(cartNew));
        when(stockClientPort.inStock(cartNew.getItemId(), cartNew.getQuantity())).thenReturn(true);

        cartUseCaseImpl.addCart(cartNew);

        verify(cartPersistencePort, times(1)).updateItemInCart(any(Cart.class));
        verify(cartPersistencePort,never()).saveCart(any(Cart.class));
    }

    @Test
    void testAddCart_CartDoesNotExist() {
        Long userId = 1L;
        when(userIdPort.getUserId()).thenReturn(userId);
        when(cartPersistencePort.findItemCartByUserIdAndItemId(cart.getItemId(), userId)).thenReturn(Optional.empty());
        when(stockClientPort.inStock(cart.getItemId(), cart.getQuantity())).thenReturn(true);
        when(cartPersistencePort.saveCart(cart)).thenReturn(cart);

        Cart result = cartUseCaseImpl.addCart(cart);

        assertEquals(cart, result);
        verify(cartPersistencePort).saveCart(cart);
    }

    @Test
    void testDeleteCart() {
        when(userIdPort.getUserId()).thenReturn(cart.getUserId());
        when(cartPersistencePort.findItemCartByUserIdAndItemId(cart.getItemId(), cart.getUserId())).thenReturn(Optional.of(cart));
        when(cartPersistencePort.deleteCart(cart.getItemId(), cart.getUserId())).thenReturn(Constants.DELETE_CART);

        String result = cartUseCaseImpl.deleteCart(cart.getItemId());

        assertEquals(Constants.DELETE_CART, result);

        verify(cartPersistencePort, times(1)).deleteCart(cart.getItemId(), cart.getUserId());
        verify(cartPersistencePort, times(1)).findItemCartByUserIdAndItemId(cart.getItemId(), cart.getUserId());
        verify(userIdPort, times(1)).getUserId();
    }

    @Test
    void deleteCart_ItemNotFound() {
        Long itemId = cart.getItemId();
        when(userIdPort.getUserId()).thenReturn(cart.getUserId());

        when(cartPersistencePort.findItemCartByUserIdAndItemId(cart.getItemId(), cart.getUserId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cartUseCaseImpl.deleteCart(itemId));

        verify(userIdPort).getUserId();
        verify(cartPersistencePort).findItemCartByUserIdAndItemId(cart.getItemId(), cart.getUserId());
        verify(cartPersistencePort, never()).deleteCart(anyLong(), anyLong());
    }

    @Test
    void testBuy() {
        when(cartPersistencePort.buy()).thenReturn(Constants.BUY);

        String result = cartUseCaseImpl.buy();

        assertEquals(Constants.BUY, result);
        verify(cartPersistencePort).buy();
    }

    @Test
    void testNotInStockValidation_WhenInStock() {
        when(stockClientPort.inStock(cart.getItemId(), cart.getQuantity())).thenReturn(true);
        assertDoesNotThrow(() -> new NotInStock(DomainConstans.NOT_IN_STOCK));
    }

    @Test
    void testNotInStockValidation_WhenNotInStock() {
        LocalDateTime now = LocalDateTime.now();

        when(userIdPort.getUserId()).thenReturn(1L);
        when(cartPersistencePort.findItemCartByUserIdAndItemId(1L, 1L)).thenReturn(Optional.empty());
        when(stockClientPort.inStock(cart.getItemId(), cart.getQuantity())).thenReturn(false);
        (when(transactionClientPort.nextSupplyDate(cart.getItemId()))).thenReturn(now);

        Exception exception = assertThrows(NotInStock.class, () -> cartUseCaseImpl.addCart(cart));

        String expectedMessage = String.format(DomainConstans.NOT_IN_STOCK, now);
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testAddCart_WithCategoryValidation() {

        when(userIdPort.getUserId()).thenReturn(cart.getUserId());
        when(cartPersistencePort.findItemCartByUserIdAndItemId(cart.getItemId(), cart.getUserId())).thenReturn(Optional.empty());
        when(stockClientPort.inStock(cart.getItemId(), cart.getQuantity())).thenReturn(true);
        when(cartPersistencePort.saveCart(cart)).thenReturn(cart);
        when(cartPersistencePort.getAllItemsByUserId(cart.getUserId())).thenReturn(List.of(new ItemCart(Status.STANDBY.toString(), 1L, LocalDateTime.now(), 1)));
        when(stockClientPort.getCategoriesByItemId(cart.getItemId())).thenReturn(List.of(1L));

        assertDoesNotThrow(() -> cartUseCaseImpl.addCart(cart));
    }

    @Test
    void testGetAllItemsInCart() {

        Long userId = 1L;
        when(userIdPort.getUserId()).thenReturn(userId);

        // Mock cart items
        List<ItemCart> itemsInCart = List.of(
                new ItemCart(Status.STANDBY.toString(), 1L, LocalDateTime.now(), 2),
                new ItemCart(Status.STANDBY.toString(), 2L, LocalDateTime.now(), 2)
        );
        when(cartPersistencePort.getAllItemsByUserId(userId)).thenReturn(itemsInCart);

        // Mock paginated items
        List<ItemResponseDTO> itemResponses = List.of(
                new ItemResponseDTO(
                        1L,
                        "Item 1",
                        "Description 1",
                        5,
                        BigDecimal.TEN,
                        List.of(new NameIdDTO(1L, "Categoria 1"), new NameIdDTO(2L, "Categoria 2")),
                        new NameIdDTO(1L, "Apple")),
                new ItemResponseDTO(
                        2L,
                        "Item 2",
                        "Description 2",
                        0,
                        BigDecimal.valueOf(20),
                        List.of(new NameIdDTO(1L, "Categoria 1"), new NameIdDTO(2L, "Categoria 2")),
                        new NameIdDTO(1L, "Apple"))

        );
        PaginatedItemResponse paginatedResponse = new PaginatedItemResponse(itemResponses, 0, 1, 2);

        ItemsInCartPaginationRequest paginationRequest = new ItemsInCartPaginationRequest(0, 2, SortDirection.ASC, Filter.CATEGORYNAME, "Electronicos");
        ItemsRequest itemsRequest = new ItemsRequest(List.of(1, 2));

        when(stockClientPort.getPaginatedItems(any(ItemsInCartPaginationRequest.class), any(ItemsRequest.class))).thenReturn(paginatedResponse);

        List<ItemsWithPrice> itemsWithPriceList = List.of(
                new ItemsWithPrice(1L, BigDecimal.TEN),
                new ItemsWithPrice(2L, BigDecimal.valueOf(20))
        );
        when(stockClientPort.getItemsWithPrice(any(ItemsRequest.class))).thenReturn(itemsWithPriceList);


        LocalDateTime nextSupplyDate = LocalDateTime.now();

        // Mock next supply date
        when(transactionClientPort.nextSupplyDate(2L)).thenReturn(nextSupplyDate);


        ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> result = cartUseCaseImpl.getAllItemsInCart(paginationRequest);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getItems().size());
        assertEquals(BigDecimal.valueOf(60), result.getTotalPrice());

        ItemsWithNextSupplyDate item1 = result.getItems().get(0);
        assertEquals(1L, item1.getId());
        assertEquals(2, item1.getQuantityInCart());
        assertTrue(item1.getAreStock());

        ItemsWithNextSupplyDate item2 = result.getItems().get(1);
        assertEquals(2L, item2.getId());
        assertEquals(2, item2.getQuantityInCart());
        assertFalse(item2.getAreStock());
        assertEquals(nextSupplyDate, item2.getNextSupplyDate());

        verify(userIdPort, times(1)).getUserId();
        verify(cartPersistencePort, times(1)).getAllItemsByUserId(userId);
        verify(stockClientPort, times(1)).getPaginatedItems(any(ItemsInCartPaginationRequest.class), any(ItemsRequest.class));
        verify(stockClientPort, times(1)).getItemsWithPrice(any(ItemsRequest.class));
        verify(transactionClientPort, times(1)).nextSupplyDate(2L);
    }

}
