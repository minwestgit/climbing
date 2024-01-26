package com.ms.climbing.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LoginRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;

    @Builder
    private LoginRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
