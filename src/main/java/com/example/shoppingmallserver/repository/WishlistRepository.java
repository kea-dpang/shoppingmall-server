package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.Wishlist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
