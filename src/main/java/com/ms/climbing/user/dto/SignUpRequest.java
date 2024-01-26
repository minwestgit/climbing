package com.ms.climbing.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignUpRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    private String nickName;
    private String instagram;

    @Builder
    private SignUpRequest(String userId, String password, String name, String nickName, String instagram) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.instagram = instagram;
    }
}
