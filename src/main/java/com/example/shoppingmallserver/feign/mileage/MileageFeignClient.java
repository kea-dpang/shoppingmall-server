package com.example.shoppingmallserver.feign.mileage;

import org.springframework.cloud.openfeign.FeignClient;
import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.base.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Mileage 서비스와의 통신을 위한 Feign 클라이언트 인터페이스입니다.
 * 이 인터페이스를 통해 Mileage 서비스의 마일리지 생성 및 삭제 API를 호출할 수 있습니다.
 */
@FeignClient(name = "mileage-server")
public interface MileageFeignClient {

    /**
     * 사용자의 마일리지를 생성하는 API를 호출하는 메서드입니다.
     * @param userId 마일리지 생성에 필요한 사용자 ID
     * @return API 호출의 결과를 담은 ResponseEntity 객체 (굳이 DTO로 받을 이유는 없긴 함)
     */
    @PostMapping("/api/mileage")
    ResponseEntity<SuccessResponse<MileageResponseDto>> createMileage(@RequestParam Long userId);

    /**
     * 사용자의 마일리지를 삭제하는 API를 호출하는 메서드입니다.
     * @param userId 마일리지 생성에 필요한 사용자 ID
     * @return API 호출의 결과를 담은 ResponseEntity 객체 (굳이 DTO로 받을 이유는 없지만 오류를 없애기 위함)
     */
    @DeleteMapping("/api/mileage")
    ResponseEntity<BaseResponse> deleteMileage(@RequestParam Long userId);


}
