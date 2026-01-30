package com.toptier.web.dto;

public record ResultResponse<T> (
    Boolean success,
    String message,
    String errorCode,
    T data
){
    public static <T> ResultResponse<T> success(String message, T data) {
        return new ResultResponse(true, message, null, data);
    }

    public static <T> ResultResponse<T> fail(String message, String errorCode) {
        return new ResultResponse(false, message, errorCode, null);
    }
}
