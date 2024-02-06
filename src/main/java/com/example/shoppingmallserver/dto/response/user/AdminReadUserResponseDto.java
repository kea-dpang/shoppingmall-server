package com.example.shoppingmallserver.dto.response.user;

import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 관리자가 조회하는 사용자 정보를 담는 DTO 클래스입니다.
 * 사용자 ID, 사원 번호, 이름, 이메일, 입사일, 기본 주소 정보를 포함합니다.
 */
@Getter
public class AdminReadUserResponseDto {
    private final Long userId;
    private final Long employeeNumber;
    private final String name;
    private final String email;
    private final LocalDate joinDate;
    private final String defaultAddress;

    /**
     * UserDetail 엔티티를 이용하여 AdminReadUserDto를 생성합니다.
     *
     * @param userDetail 관리자가 조회하는 사용자의 상세 정보를 담은 엔티티
     */
    public AdminReadUserResponseDto(User user, UserDetail userDetail) {
        this.userId = userDetail.getUser().getId();
        this.employeeNumber = userDetail.getEmployeeNumber();
        this.name = userDetail.getName();
        this.email = maskEmail(user.getEmail());
        this.joinDate = userDetail.getJoinDate();
        this.defaultAddress = maskAddress(userDetail.getAddress());
    }

    /**
     * 이메일 주소를 마스킹 처리합니다.
     *
     * @param email 마스킹 처리할 이메일 주소
     * @return 마스킹 처리된 이메일 주소
     */
    private String maskEmail(String email) {
        int index = email.indexOf("@");
        if (index <= 1) {
            return email;
        } else {
            return email.charAt(0) + email.substring(1, index).replaceAll("\\.", "*") + email.substring(index);
        }
    }

    private String maskAddress(String address) {
        String maskedZipCode = "****";
        String[] addressParts = address.split(" ", 3);
        String maskedAddress = addressParts[0] + " " + addressParts[1] + " ****";
        return maskedZipCode + " " + maskedAddress;
    }


}
