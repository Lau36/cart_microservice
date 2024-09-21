package com.example.cart_microservice.infrastructure.configuration.excepcionhandler;

import com.example.cart_microservice.domain.exceptions.CannotAddItemToCart;
import com.example.cart_microservice.domain.exceptions.NotInStock;
import com.example.cart_microservice.infrastructure.utils.InfrastructureConstants;
import com.example.cart_microservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    @ExceptionHandler(NotInStock.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFieldException(NotInStock e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(InfrastructureConstants.NOT_IN_STOCK_MESSAGE, e.getMessage()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(new ExceptionResponse(
                e.getReason(),
                e.getStatusCode().toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(CannotAddItemToCart.class)
    public ResponseEntity<ExceptionResponse> handleCannotAddItemToCart(CannotAddItemToCart e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
           String.format(Constants.CANNOT_ADD_ITEM_TO_CART_MESSAGE, e.getMessage()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }
}
