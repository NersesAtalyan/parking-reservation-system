package com.parking.system.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BusinessExceptionType {

    COMMUNITY_ALREADY_ACTIVE("Community is already active."),
    COMMUNITY_ALREADY_DEACTIVATED("Community is already deactivated."),

    SPOT_ALREADY_RESERVED("Spot is already reserved."),
    SPOT_NOT_YOURS("You can't release someone else's spot");

    private final String message;

    public String getCode() {
        return name();
    }

}
