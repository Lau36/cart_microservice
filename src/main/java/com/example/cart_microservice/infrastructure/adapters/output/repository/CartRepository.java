package com.example.cart_microservice.infrastructure.adapters.output.repository;

import com.example.cart_microservice.infrastructure.adapters.output.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findByIdUser(Long userId);
}
