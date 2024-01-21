package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.cart.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId);
}
