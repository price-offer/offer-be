package com.offer.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final int code;
    private final String message;

    @JsonInclude
    private final T data;

    public static <T> ApiResponse<T> of(ResponseMessage responseMessage, T data) {
        return new ApiResponse(responseMessage.getCode(), responseMessage.getMessage(), data);
    }

    public static ApiResponse of(ResponseMessage responseMessage) {
        return new ApiResponse(responseMessage.getCode(), responseMessage.getMessage(), null);
    }
}
