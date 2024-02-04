package com.example.shoppingmallserver.dto.request.user;

import com.example.shoppingmallserver.entity.user.WithdrawalReason;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "탈퇴 정보")
@Getter
public class WithdrawalRequestDto {
    @Schema(description = "비밀번호 확인", example = "qwer1234!@#$")
    private final String oldPassword;
    @Schema(description = "탈퇴 사유(enum)")
    private final List<WithdrawalReason> reason;
    @Schema(description = "남길 말씀", example = "서비스가 맘에 안드네요")
    private final String message;

    public WithdrawalRequestDto(List<WithdrawalReason> reason, String message, String oldPassword) {
        this.oldPassword = oldPassword;
        this.reason = reason;
        this.message = message;
    }
}
