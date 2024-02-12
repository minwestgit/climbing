package com.ms.climbing.climb.controller;

import com.ms.climbing.climb.dto.CreateClimbRequest;
import com.ms.climbing.climb.dto.GetClimbResponse;
import com.ms.climbing.climb.service.ClimbService;
import com.ms.climbing.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClimbController {

    private final ClimbService climbService;

    @Operation(summary = "클라이밍 기록 추가")
    @PostMapping(value = "/climbs")
    public ApiResponse<Void> create(@RequestBody @Valid CreateClimbRequest request) {
        climbService.create(request);
        return ApiResponse.success();
    }

    @Operation(summary = "클라이밍 기록 조회")
    @GetMapping(value = "/climbs/{id}")
    public ApiResponse<GetClimbResponse> get(@PathVariable Long id) {
        return ApiResponse.success(climbService.get(id));
    }

    @Operation(summary = "클라이밍 기록 삭제")
    @DeleteMapping(value = "/climbs/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        climbService.delete(id);
        return ApiResponse.success();
    }
}
