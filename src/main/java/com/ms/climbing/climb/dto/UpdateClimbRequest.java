package com.ms.climbing.climb.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateClimbRequest {
    private Long id;
    private String description;
    private String date;
    private String place;
    private List<CreateBoulderingRequest> updateBoulderingList;
    private List<Long> deleteBoulderingIds;
}
