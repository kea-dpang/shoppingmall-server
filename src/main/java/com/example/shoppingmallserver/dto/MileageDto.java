package com.example.shoppingmallserver.dto;

public class MileageDto {
    public final Long userId;
    public final int mileage;
    public final int personalChargedMileage;

    public MileageDto(Long userId, int mileage, int personalChargedMileage) {
        this.userId = userId;
        this.mileage = mileage;
        this.personalChargedMileage = personalChargedMileage;
    }
}
