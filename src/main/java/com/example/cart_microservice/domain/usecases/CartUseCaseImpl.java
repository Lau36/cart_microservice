package com.example.cart_microservice.domain.usecases;

import com.example.cart_microservice.domain.exceptions.CannotAddItemToCart;
import com.example.cart_microservice.domain.exceptions.NotFoundException;
import com.example.cart_microservice.domain.exceptions.NotInStock;
import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.models.ItemCart;
import com.example.cart_microservice.domain.ports.input.ICartUseCase;
import com.example.cart_microservice.domain.ports.output.ICartPersistencePort;
import com.example.cart_microservice.domain.ports.output.IStockClientPort;
import com.example.cart_microservice.domain.ports.output.ITransactionClientPort;
import com.example.cart_microservice.domain.ports.output.IUserId;
import com.example.cart_microservice.domain.utils.DomainConstans;
import com.example.cart_microservice.domain.utils.paginationitems.*;
import com.example.cart_microservice.domain.utils.paginationitems.ItemsWithPrice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CartUseCaseImpl implements ICartUseCase {
    private final ICartPersistencePort cartPersistencePort;
    private final IStockClientPort stockClientPort;
    private final ITransactionClientPort transactionClientPort;
    private final IUserId userIdPort;

    public CartUseCaseImpl(ICartPersistencePort cartPersistencePort, IStockClientPort stockClientPort, ITransactionClientPort transactionClientPort, IUserId userIdPort) {
        this.cartPersistencePort = cartPersistencePort;
        this.stockClientPort = stockClientPort;
        this.transactionClientPort = transactionClientPort;
        this.userIdPort = userIdPort;
    }

    @Override
    public Cart addCart(Cart cart) {
        Long userId = userIdPort.getUserId();
        Optional<Cart> cartOptional = cartPersistencePort.findItemCartByUserIdAndItemId(cart.getItemId(), userId);
        if(cartOptional.isPresent()) {
            notInStockValidation(cart);
            Cart cartToUpdate = cartOptional.get();
            return processCartUpdate(cartToUpdate, cart.getQuantity());
        }
        addItemValidations(cart, userId);
        return cartPersistencePort.saveCart(cart);
    }

    @Override
    public String deleteCart(Long itemId) {
        Long userId = userIdPort.getUserId();
        if(cartPersistencePort.findItemCartByUserIdAndItemId(itemId, userId).isEmpty()) {
            throw new NotFoundException(DomainConstans.NOT_FOUND);
        }
        return cartPersistencePort.deleteCart(itemId, userId);
    }

    @Override
    public String buy() {
        return cartPersistencePort.buy();
    }

    @Override
    public ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> getAllItemsInCart(ItemsInCartPaginationRequest pagination) {
        Long userId = userIdPort.getUserId();

        List<ItemCart> items = cartPersistencePort.getAllItemsByUserId(userId);
        List<Long> itemsId = items.stream().map(ItemCart::getItemId).toList();
        ItemsRequest itemsRequest = new ItemsRequest(itemsId.stream().map(Long::intValue).toList());

        PaginatedItemResponse itemsPaginated = paginatedItems(pagination, itemsRequest);

        ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> itemsWithPrice = generateTotalPrice(itemsPaginated, items, itemsRequest);

        return generateNextSupplyDate(itemsWithPrice);
    }

    private ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> generateTotalPrice(PaginatedItemResponse paginatedItems, List<ItemCart> itemsInCart, ItemsRequest itemsRequest){
        Map<Long, Integer> itemsMap = itemsInCart.stream().collect(Collectors.toMap(ItemCart::getItemId, ItemCart::getQuantity));

        ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> addQuantityInCartToItemsPaginated = addQuantityInCart(paginatedItems, itemsMap);
        BigDecimal totalPrice = new BigDecimal(DomainConstans.ZERO);

        List<ItemsWithPrice> itemsWithPriceList = stockClientPort.getItemsWithPrice(itemsRequest);

        for(ItemsWithPrice item : itemsWithPriceList){
            if(itemsMap.containsKey(item.getId())){
                BigDecimal total =  item.getPrice().multiply(BigDecimal.valueOf(itemsMap.get(item.getId())));
                totalPrice = totalPrice.add(total);
            }
        }
        addQuantityInCartToItemsPaginated.setTotalPrice(totalPrice);

        return addQuantityInCartToItemsPaginated;
    }

    private ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> generateNextSupplyDate(ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> items){

        for(ItemsWithNextSupplyDate item : items.getItems()){
            if(item.getQuantityInCart() > item.getQuantityInStock() || Objects.equals(item.getQuantityInStock(), DomainConstans.ZERO)){
                item.setAreStock(Boolean.FALSE);
                item.setNextSupplyDate(transactionClientPort.nextSupplyDate(item.getId()));
            }
        }
        return items;
    }

    private ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> addQuantityInCart(PaginatedItemResponse paginatedItems, Map<Long, Integer> itemsMap){

        ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> items = getItemsPaginated(paginatedItems);

        for (ItemsWithNextSupplyDate item : items.getItems()){
            if(itemsMap.containsKey(item.getId())){
                item.setQuantityInCart(itemsMap.get(item.getId()));
            }
        }
        return items;
    }

    private ItemsPaginatedWithPrice<ItemsWithNextSupplyDate> getItemsPaginated(PaginatedItemResponse paginatedResponse){
        List<ItemsWithNextSupplyDate> transformedItems = paginatedResponse.getItems().stream().map(item -> new ItemsWithNextSupplyDate(
                item.getId(),
                item.getName(),
                item.getDescription(),
                null,
                item.getQuantityInStock(),
                true,
                null,
                item.getPrice(),
                item.getCategories(),
                item.getBrand()
        )).toList();

        return new ItemsPaginatedWithPrice<>(
                transformedItems,
                null,
                paginatedResponse.getCurrentPage(),
                paginatedResponse.getTotalPages(),
                paginatedResponse.getTotalElements()
        );
    }

    private PaginatedItemResponse paginatedItems(ItemsInCartPaginationRequest paginationRequest, ItemsRequest itemsRequest) {
        return stockClientPort.getPaginatedItems(paginationRequest, itemsRequest);
    }



    private Cart processCartUpdate(Cart cart, Integer quantity) {
        if(Boolean.TRUE.equals(cart.getDeleted())){
            return updateDeletingItem(cart, quantity);
        }
        else{
            return updateCart(cart, quantity);
        }
    }

    private Cart updateCart(Cart cartToUpdate, Integer quantity){
        cartToUpdate.setQuantity(cartToUpdate.getQuantity() + quantity);
        cartToUpdate.setUpdatedAt(LocalDateTime.now());
        return cartPersistencePort.updateItemInCart(cartToUpdate);
    }

    private Cart updateDeletingItem(Cart cartToUpdate, Integer quantity) {
        cartToUpdate.setQuantity(quantity);
        cartToUpdate.setUpdatedAt(LocalDateTime.now());
        cartToUpdate.setDeleted(Boolean.FALSE);
        return cartPersistencePort.updateItemInCart(cartToUpdate);
    }

    
    private void addItemValidations(Cart cart, Long userId) {
        notInStockValidation(cart);

        if(Boolean.FALSE.equals(canAddItemToCart(userId, cart.getItemId()))){
            throw new CannotAddItemToCart(DomainConstans.CANNOT_ADD_ITEM_TO_CART_MESSAGE);
        }
    }

    private void notInStockValidation(Cart cart){
        if(Boolean.FALSE.equals(stockClientPort.inStock(cart.getItemId(), cart.getQuantity()))) {
            throw new NotInStock(String.format(DomainConstans.NOT_IN_STOCK, transactionClientPort.nextSupplyDate(cart.getItemId())));
        }
    }

    private Map<Long, Integer> getCategoryCountsForItemsInCart(Long userId) {
        List<ItemCart>  itemsInCart = cartPersistencePort.getAllItemsByUserId(userId);
        Map<Long, Integer> categoryCounts = new HashMap<>();

        for(ItemCart item : itemsInCart) {
            List<Long> categoriesByItem = stockClientPort.getCategoriesByItemId(item.getItemId());
            for (Long categoryId : categoriesByItem) {
                categoryCounts.put(categoryId, categoryCounts.getOrDefault(categoryId, DomainConstans.ZERO) + DomainConstans.ONE);
            }
        }
        return categoryCounts;
    }

    private boolean canAddItemToCart(Long userId, Long itemId) {
        Map<Long, Integer> categoryCounts = getCategoryCountsForItemsInCart(userId);
        List<Long> newItemCategories = stockClientPort.getCategoriesByItemId(itemId);

        for(Long newCategoryId : newItemCategories) {
            if(categoryCounts.getOrDefault(newCategoryId,  DomainConstans.ZERO) >=
                    DomainConstans.MAXIMUN_NUMBERS_OF_CATEGORIES) {
                return false;
            }
        }
        return true;
    }
}
