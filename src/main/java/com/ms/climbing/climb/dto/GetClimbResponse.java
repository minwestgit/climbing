package com.ms.climbing.climb.dto;

import com.ms.climbing.climb.entity.Climb;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetClimbResponse {
    private Long id;
    private String description;
    private String date;
    private String place;
    private List<GetBoulderingResponse> boulderingList;

    public static GetClimbResponse of(Climb climb, List<GetBoulderingResponse> boulderingList) {
        return new GetClimbResponse(
            climb.getId(),
            climb.getDescription(),
            climb.getDate(),
            climb.getPlace(),
            boulderingList
        );
    }
}
