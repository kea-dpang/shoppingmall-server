package com.example.shoppingmallserver.feign.mileage;

public class MileageResponseDto {
    public final Long userId;
    public final int mileage;
    public final int personalChargedMileage;

    public MileageResponseDto(Long userId, int mileage, int personalChargedMileage) {
        this.userId = userId;
        this.mileage = mileage;
        this.personalChargedMileage = personalChargedMileage;
    }
}
