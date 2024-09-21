package com.example.cart_microservice.infrastructure.adapters.output.repository;

import com.example.cart_microservice.infrastructure.adapters.output.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findByUserId(Long userId);
    Optional<CartEntity> findByItemIdAndUserId(Long itemId, Long userId);
}
