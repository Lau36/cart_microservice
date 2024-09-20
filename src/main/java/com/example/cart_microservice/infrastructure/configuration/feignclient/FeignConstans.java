package com.example.cart_microservice.infrastructure.configuration.feignclient;

public class FeignConstans {
    private FeignConstans() {
    }

    public static final String TOKEN_START_WITH = "Bearer ";
    public static final String ERROR_WITH_MICROSERVICE = "Ocurrio un error con el microservicio";
    public static final String MESSAGE = "message";
    public static final String ERROR = "No se pudo parsear el mensaje de error.";
    public static final String ERROR_WITH_MICROSERVICE_CONEXION = "Ocurrio un error de conexión con el microservicio";

}
