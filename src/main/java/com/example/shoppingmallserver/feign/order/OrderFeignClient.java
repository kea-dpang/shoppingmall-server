package com.example.shoppingmallserver.feign.order;

import com.example.shoppingmallserver.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Mileage 서비스와의 통신을 위한 Feign 클라이언트 인터페이스입니다.
 * 이 인터페이스를 통해 Mileage 서비스의 마일리지 생성 및 삭제 API를 호출할 수 있습니다.
 */
@FeignClient(name = "mileage-server")
public interface OrderFeignClient {

    // 사용자 탈퇴에 따른 주문 삭제를 위한 Feign 요청
    @DeleteMapping("/api/orders/{userId}")
    ResponseEntity<BaseResponse> deleteOrder(@PathVariable Long userId);

    // Todo: 아마 취소 삭제도 있을 것 같음

}
