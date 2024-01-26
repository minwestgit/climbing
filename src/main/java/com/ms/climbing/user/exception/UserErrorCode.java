package com.ms.climbing.user.exception;

import com.ms.climbing.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    DUPLICATE_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 ID 입니다."),
    ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}