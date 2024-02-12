package com.ms.climbing.climb.dto;

import com.ms.climbing.climb.entity.BoulderingLevel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateBoulderingRequest {
    private BoulderingLevel level;
    private int num;
}
