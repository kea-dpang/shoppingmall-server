package com.example.shoppingmallserver.dto.request.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteListRequestDto {
    private final List<Long> userIds;

    @JsonCreator
    public DeleteListRequestDto(@JsonProperty("userIds") List<Long> userIds) {
        this.userIds = userIds;
    }
}
