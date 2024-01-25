package com.ms.climbing.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
  String name();
  HttpStatus getHttpStatus();
  String getMessage();
}
