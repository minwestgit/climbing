package com.ms.climbing.user.exception;

import com.ms.climbing.exception.CommonException;
import lombok.Getter;

@Getter
public class UserException extends CommonException {
    public UserException(UserErrorCode adminErrorCode) {
        super(adminErrorCode);
    }
}