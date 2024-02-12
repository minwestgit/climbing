package com.ms.climbing.climb.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateBoulderingRequest {
    private String color;
    private int num;
}
