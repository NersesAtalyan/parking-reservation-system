package com.parking.system.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCodes implements BusinessErrorCode {
    ALREADY_ACTIVE("Entity is already active."),
    ALREADY_DEACTIVATED("Entity is already deactivated.");
    private final String defaultMessage;

    @Override
    public String getCode() {
        return this.name();
    }
}