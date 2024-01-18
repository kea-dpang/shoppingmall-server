package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.mileage.Mileage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageRepository extends JpaRepository<Mileage, Long> {
}
