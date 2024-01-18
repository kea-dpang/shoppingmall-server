package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.ChargeMileageDto;
import com.example.shoppingmallserver.dto.UpdateMileageStatusDto;
import com.example.shoppingmallserver.entity.mileage.Mileage;
import com.example.shoppingmallserver.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MileageService {

    // 마일리지 충전 신청
    Mileage requestMileageRecharge(Long userId, ChargeMileageDto chargeMileageDto);

    // ==========================관리자===========================

    // 관리자의 마일리지 신청자 리스트 조회
    List<User> getMileageList(Optional<String> keyword);

    // 관리자의 마일리지 신청 승인 상태 수정(자료형 수정 필)
    Mileage updateMileageStatus(Long userId, UpdateMileageStatusDto updateMileageStatusDto);

}
