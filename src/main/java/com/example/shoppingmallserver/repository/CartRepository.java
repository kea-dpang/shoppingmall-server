package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
