package com.example.cart_microservice.infrastructure.adapters.input.controller.utils;

public class SwaggerConstants {

    private SwaggerConstants() {
    }

    public static final String ADD_CART = "Añadir ítem al carrito";
    public static final String ADD_CART_DESCRIPTION = "Crea un nuevo carrito añadiendo un ítem al mismo";
    public static final String DELETE_ITEM_CART = "Eliminar ítem del carrito";
    public static final String DELETE_ITEM_CART_DESCRIPTION = "Elimina un ítem específico del carrito";
    public static final String BUY_CART = "Comprar carrito";
    public static final String BUY_CART_DESCRIPTION = "Procesa la compra de todos los ítems en el carrito";
    public static final String ADDED_CART = "Carrito creado con éxito";
    public static final String DELETED_ITEM_CART = "Ítem eliminado del carrito con éxito";
    public static final String PURCHASED_CART = "Compra realizada con éxito";
    public static final String BAD_REQUEST = "Solicitud incorrecta";
    public static final String NOT_FOUND = "No se encontró el articulo para eliminar";
    public static final String BEARER_TOKEN = "bearerAuth";
    public static final String SERVICE_DOWN = "Ocurrió un error de conexión con algún microservicio";
}
