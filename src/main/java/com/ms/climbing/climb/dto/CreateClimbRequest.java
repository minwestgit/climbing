package com.ms.climbing.climb.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateClimbRequest {
    private String description;
    private String date;
    private String place;
    private List<CreateBoulderingRequest> boulderingList;
}
