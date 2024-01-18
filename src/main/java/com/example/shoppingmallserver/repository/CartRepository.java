package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.cart.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Long> {
}
