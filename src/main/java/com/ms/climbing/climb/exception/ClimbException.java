package com.ms.climbing.climb.exception;

import com.ms.climbing.exception.CommonException;
import lombok.Getter;

@Getter
public class ClimbException extends CommonException {
    public ClimbException(ClimbErrorCode adminErrorCode) {
        super(adminErrorCode);
    }
}