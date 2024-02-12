package com.ms.climbing.user.controller;

import com.ms.climbing.common.ApiResponse;
import com.ms.climbing.security.JwtTokenProvider;
import com.ms.climbing.security.TokenDto;
import com.ms.climbing.user.dto.LoginRequest;
import com.ms.climbing.user.dto.SignUpRequest;
import com.ms.climbing.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "회원가입")
    @PostMapping(value = "/signup")
    public ApiResponse<Void> signup(@RequestBody @Valid SignUpRequest request) {
        userService.signup(request);
        return ApiResponse.success();
    }

    @Operation(summary = "로그인")
    @PostMapping(value = "/login")
    public ApiResponse<TokenDto> login(@RequestBody @Valid LoginRequest request) {
        return ApiResponse.success(userService.login(request));
    }

    @Operation(summary = "로그아웃")
    @PostMapping(value = "/logout")
    public ApiResponse<String> logout() {
        userService.logout();
        return ApiResponse.success();
    }

    @Operation(summary = "Access Token 재발급")
    @GetMapping(value = "/refresh/token")
    public ApiResponse<String> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.extractToken(request);
        return ApiResponse.success(userService.refreshToken(refreshToken));
    }

}
