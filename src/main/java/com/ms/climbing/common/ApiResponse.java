package com.ms.climbing.common;

import com.ms.climbing.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ApiResponse<T> {

    private HttpStatus status;
    private String code;
    private T data;
    private String message;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .code("SUCCESS")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .code("SUCCESS")
                .build();
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return ApiResponse.<T>builder()
                .status(errorCode.getHttpStatus())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }
}
