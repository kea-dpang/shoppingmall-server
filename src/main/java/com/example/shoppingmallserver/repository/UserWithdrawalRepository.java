package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.user.UserWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWithdrawalRepository extends JpaRepository<UserWithdrawal, Long> {
}
