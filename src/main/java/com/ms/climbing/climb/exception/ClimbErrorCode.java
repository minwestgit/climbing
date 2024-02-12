package com.ms.climbing.climb.exception;

import com.ms.climbing.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ClimbErrorCode implements ErrorCode {
    CLIMB_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 클라이밍 기록입니다."),
    BOULDERING_LEVEL_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 볼더링 레벨입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}