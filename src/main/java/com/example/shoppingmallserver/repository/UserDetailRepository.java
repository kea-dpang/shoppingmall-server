package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.user.UserDetail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    List<UserDetail> findByNameContaining(String keyword);

    Optional<UserDetail> findByEmail(String email);
}
