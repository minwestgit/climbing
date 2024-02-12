package com.ms.climbing.climb.dto;

import com.ms.climbing.climb.entity.Bouldering;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetBoulderingResponse {
    private Long id;
    private String color;
    private int num;

    public static GetBoulderingResponse of(Bouldering bouldering) {
        return new GetBoulderingResponse(
                bouldering.getId(),
                bouldering.getColor(),
                bouldering.getNum()
        );
    }
}
