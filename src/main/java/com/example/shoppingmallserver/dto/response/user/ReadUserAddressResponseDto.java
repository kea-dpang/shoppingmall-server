package com.example.shoppingmallserver.dto.response.user;

import com.example.shoppingmallserver.entity.user.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserAddressResponseDto {
    public String name;
    public String phoneNumber;
    public String zipCode;
    public String address;
    public String detailAddress;

    public ReadUserAddressResponseDto(UserDetail userDetail) {
        name = userDetail.getName();
        phoneNumber = userDetail.getPhoneNumber();
        zipCode = userDetail.getZipCode();
        address = userDetail.getAddress();
        detailAddress = userDetail.getDetailAddress();
    }
}
