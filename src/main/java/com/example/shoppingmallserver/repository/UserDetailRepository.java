package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.user.UserDetail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
