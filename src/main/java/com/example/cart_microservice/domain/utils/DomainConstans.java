package com.example.cart_microservice.domain.utils;

public class DomainConstans {
    private DomainConstans() {
    }

    public static final String NOT_IN_STOCK = "No hay stock de ese articulo, la proxima fecha seria el: %s";
    public static final String CANNOT_ADD_ITEM_TO_CART_MESSAGE = "No se puede añadir el item al carrito";
    public static final String NOT_FOUND = "No se encontró el item";
    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer MAXIMUN_NUMBERS_OF_CATEGORIES = 3;
}
