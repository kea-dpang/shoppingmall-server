package com.example.shoppingmallserver.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteListDto {
    private final List<Long> userIds;

    public DeleteListDto (List<Long> userIds) {
        this.userIds = userIds;
    }
}
