package com.example.shoppingmallserver.feign.auth;

import com.example.shoppingmallserver.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthFeignClient {

    // 회원 탈퇴 Feign
    @DeleteMapping("/users/{id}")
    ResponseEntity<BaseResponse> deleteUser(@PathVariable("id") Long id);
}
