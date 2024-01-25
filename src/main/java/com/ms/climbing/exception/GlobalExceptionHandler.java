package com.ms.climbing.exception;

import com.ms.climbing.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    protected ApiResponse<ErrorCode> handleCommonException(CommonException e) {
        log.error("CommonException -> code:{}, message:{}", e.getErrorCode(), e.getErrorCode().getMessage());
        return ApiResponse.error(e.getErrorCode());
    }
}