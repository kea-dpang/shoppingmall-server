package com.example.shoppingmallserver.dto.response.user;

import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * 관리자가 조회하는 사용자 정보를 담는 DTO 클래스입니다.
 * 사용자 ID, 사원 번호, 이름, 이메일, 입사일, 기본 주소 정보를 포함합니다.
 */
@Getter
@Slf4j
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
        this.name = maskName(userDetail.getName());
        this.email = maskEmail(user.getEmail());
        this.joinDate = userDetail.getJoinDate();
        this.defaultAddress = maskAddress(userDetail.getZipCode(), userDetail.getAddress());
    }

    /**
     * 사용자 이름을 마스킹 처리합니다.
     *
     * @param name 마스킹 처리할 사용자 이름
     * @return 마스킹 처리된 사용자 이름
     */
    private String maskName(String name) {
        if (name.length() <= 1) {
            return name;
        } else if (name.length() == 2) {
            return name.charAt(0) + "*";
        } else {
            return name.charAt(0) + "*".repeat(name.length() - 2) + name.charAt(name.length() - 1);
        }
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
            String newEmail = email.charAt(0) + email.substring(1, index).replaceAll("\\.", "*") + email.substring(index);
            log.info("이메일 첫글자는 email.charAt(0): {}", email.charAt(0));
            log.info("마스킹된 이메일의 두번째 글자는 {}", newEmail.charAt(1));
            log.info("마스킹된 이메일은 {}", newEmail);
            return newEmail;
        }
    }

    /**
     * 주어진 우편번호와 주소를 마스킹 처리합니다.
     * 우편번호는 전체가 '*', 주소는 처음 두 단어 이후가 '****'로 마스킹 됩니다.
     *
     * @param zipCode 마스킹 처리할 우편번호
     * @param address 마스킹 처리할 주소
     * @return 마스킹 처리된 우편번호와 주소
     */
    private String maskAddress(String zipCode, String address) {
        String maskedZipCode = maskZipCode(zipCode);
        if (address == null || address.isEmpty()) {
            // 주소가 없을 경우 (null)
            return null;
        } else {
            String[] addressParts = address.split(" ", 3);
            String maskedAddress;
            if (addressParts.length >= 2) {
                // 주소가 세 단어 이상일 경우 ex) 경기도 수원시 ***
                maskedAddress = addressParts[0] + " " + addressParts[1] + " ****";
            } else if (addressParts.length == 1) {
                // 주소가 두 단어일 경우 ex) 서울시 ***
                maskedAddress = addressParts[0] + " ****";
            } else {
                // 주소가 한 단어일 경우 ex) 서울
                maskedAddress = address;
            }
            return maskedZipCode + " " + maskedAddress;
        }
    }

    /**
     * 주어진 우편번호를 '*'로 마스킹 처리합니다.
     * 우편번호의 길이만큼 '*'로 변경됩니다.
     *
     * @param zipCode 마스킹 처리할 우편번호
     * @return 마스킹 처리된 우편번호
     */
    private String maskZipCode(String zipCode) {
        char[] maskedChars =  new char[zipCode.length()];
        Arrays.fill(maskedChars, '*');
        return new String(maskedChars);
    }
}
