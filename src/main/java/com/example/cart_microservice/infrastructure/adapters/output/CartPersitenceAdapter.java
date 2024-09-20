package com.example.cart_microservice.infrastructure.adapters.output;

import com.example.cart_microservice.domain.models.Cart;
import com.example.cart_microservice.domain.ports.output.ICartPersistencePort;
import com.example.cart_microservice.infrastructure.adapters.output.entity.CartEntity;
import com.example.cart_microservice.infrastructure.adapters.output.mapper.CartMapper;
import com.example.cart_microservice.infrastructure.adapters.output.repository.CartRepository;
import com.example.cart_microservice.infrastructure.utils.Status;
import com.example.cart_microservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class CartPersitenceAdapter implements ICartPersistencePort {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public Cart saveCart(Cart cart) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getDetails().toString();
        LocalDateTime updatedDate = LocalDateTime.now();
        CartEntity cartEntity = new CartEntity(null, Long.valueOf(userId), cart.getIdItem(), cart.getQuantity(),updatedDate, Status.STANDBY.toString(), Boolean.FALSE);
        CartEntity cartSaved = cartRepository.save(cartEntity);
        return cartMapper.toCart(cartSaved);
    }

    @Override
    public List<Cart> getAllItemsByUserId(Long userId) {
        List<CartEntity> itemsEntities = cartRepository.findByIdUser(userId);
        return cartMapper.toCarts(itemsEntities);
    }

    @Override
    public String deleteCart() {
        return Constants.DELETE_CART;
    }

    @Override
    public String buy() {
        return Constants.BUY;
    }

}
