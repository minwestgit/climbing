package com.ms.climbing.climb.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ms.climbing.climb.exception.ClimbErrorCode;
import com.ms.climbing.climb.exception.ClimbException;
import org.apache.commons.lang3.StringUtils;

public enum BoulderingLevel {
    RED, ORANGE, YELLOW, GREEN, BLUE, NAVY, PURPLE, GRAY, BLACK,
    ;

    @JsonCreator
    public static BoulderingLevel from(String inputValue) {
        for(BoulderingLevel level : BoulderingLevel.values()) {
            if(level.toString().equals(StringUtils.upperCase(inputValue))) {
                return level;
            }
        }
        throw new ClimbException(ClimbErrorCode.BOULDERING_LEVEL_NOT_FOUND);
    }
}
