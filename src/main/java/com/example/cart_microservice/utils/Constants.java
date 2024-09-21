package com.example.cart_microservice.utils;

public class Constants {

    private Constants(){

    }

    public static final String TOKEN_KEY = "JWT_SECRET";
    public static final String TOKEN_START_WITH = "Bearer ";
    public static final String DELETE_CART = "Eliminando items del carrito";
    public static final String BUY = "Comprando cosas";
    public static final String FAILED = "Falló el proceso de JWT en: ";
    public static final String CANNOT_ADD_ITEM_TO_CART_MESSAGE= "%s porque ya hay 3 articulos asociados a la misma categoria, revisa nuevamente";

}
