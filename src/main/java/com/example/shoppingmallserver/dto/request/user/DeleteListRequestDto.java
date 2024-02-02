package com.example.shoppingmallserver.dto.request.user;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteListRequestDto {
    private final List<Long> userIds;

    public DeleteListRequestDto(List<Long> userIds) {
        this.userIds = userIds;
    }
}
