package com.example.cart_microservice.infrastructure.configuration;

import com.example.cart_microservice.aplication.services.CartService;
import com.example.cart_microservice.domain.ports.output.ICartPersistencePort;
import com.example.cart_microservice.domain.ports.output.IStockClientPort;
import com.example.cart_microservice.domain.ports.output.ITransactionClientPort;
import com.example.cart_microservice.domain.ports.output.IUserId;
import com.example.cart_microservice.domain.usecases.CartUseCaseImpl;
import com.example.cart_microservice.infrastructure.adapters.StockClientAdapter;
import com.example.cart_microservice.infrastructure.adapters.TransactionClientAdapter;
import com.example.cart_microservice.infrastructure.adapters.UserIdAdapter;
import com.example.cart_microservice.infrastructure.adapters.output.CartPersitenceAdapter;
import com.example.cart_microservice.infrastructure.adapters.output.mapper.CartMapper;
import com.example.cart_microservice.infrastructure.adapters.output.mapper.ItemCartMapper;
import com.example.cart_microservice.infrastructure.adapters.output.mapper.ItemCartMapperImpl;
import com.example.cart_microservice.infrastructure.adapters.output.repository.CartRepository;
import com.example.cart_microservice.infrastructure.configuration.feignclient.StokClient;
import com.example.cart_microservice.infrastructure.configuration.feignclient.TransactionClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public ICartPersistencePort cartPersistencePort(final CartRepository cartRepository, final CartMapper cartMapper) {
        return new CartPersitenceAdapter(cartRepository, cartMapper);
    }

    @Bean
    public CartService cartService(final ICartPersistencePort cartPersistencePort, final IStockClientPort stockClientPort, final ITransactionClientPort transactionClientPort, final IUserId userId) {
        return new CartService(new CartUseCaseImpl(cartPersistencePort, stockClientPort, transactionClientPort, userId));
    }

    @Bean
    public IStockClientPort stockClientPort(final StokClient stokClient) {
        return new StockClientAdapter(stokClient);
    }

    @Bean
    public ITransactionClientPort transactionClientPort(final TransactionClient transactionClient) {
        return new TransactionClientAdapter(transactionClient);
    }

    @Bean
    public ItemCartMapper itemCartMapper() {
        return new ItemCartMapperImpl();
    }

    @Bean
    public IUserId userId(){
        return new UserIdAdapter();
    }
}
