package com.example.cart_microservice.infrastructure.adapters.output.repository;

import com.example.cart_microservice.infrastructure.adapters.output.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    @Query("SELECT b FROM CartEntity  b WHERE b.deleted = false AND b.userId = :userId")
    List<CartEntity> findByUserId(@Param("userId") Long userId);

    Optional<CartEntity> findByItemIdAndUserId(Long itemId, Long userId);
}
