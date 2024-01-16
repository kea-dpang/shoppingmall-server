package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
