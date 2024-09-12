package com.example.cart_microservice.infrastructure.configuration;

import com.example.cart_microservice.aplication.services.CartService;
import com.example.cart_microservice.domain.ports.output.ICartPersistencePort;
import com.example.cart_microservice.domain.usecases.CartUseCaseImpl;
import com.example.cart_microservice.infrastructure.adapters.output.CartPersitenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public ICartPersistencePort cartPersistencePort() {
        return new CartPersitenceAdapter();
    }

    @Bean
    public CartService cartService(final ICartPersistencePort cartPersistencePort) {
        return new CartService(new CartUseCaseImpl(cartPersistencePort));
    }
}
