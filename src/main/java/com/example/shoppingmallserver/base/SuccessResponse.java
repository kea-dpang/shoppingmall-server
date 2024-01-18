package com.example.shoppingmallserver.base;

import lombok.AllArgsConstructor;
import lombok.Data;

public class SuccessResponse<T> extends BaseResponse {
    private final T data;

    public SuccessResponse(int status, String message, T data) {
        super(status, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
