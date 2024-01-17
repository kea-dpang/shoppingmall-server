package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.ChargeMileageDto;
import com.example.shoppingmallserver.dto.UpdateMileageStatusDto;
import com.example.shoppingmallserver.entity.mileage.Mileage;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.repository.MileageRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MileageServiceImpl implements MileageService{

    private final MileageRepository mileageRepository;

    // 마일리지 충전 신청
    @Override
    public Mileage requestMileageRecharge(Long user_id, ChargeMileageDto chargeMileageDto) {
        return null;
    }

    // ==========================관리자===========================

    // 관리자의 마일리지 신청자 리스트 조회
    @Override
    public List<User> getMileageList(Optional<String> keyword) {
        return null;
    }

    // 관리자의 마일리지 신청 승인 상태 수정(자료형 수정 필)
    @Override
    public Mileage updateMileageStatus(Long user_id, UpdateMileageStatusDto updateMileageStatusDto) {
        return null;
    }
}
