package com.parking.system.resident.presets;

import com.parking.system.common.exception.BusinessErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResidentErrorCodes implements BusinessErrorCode {
    EMAIL_ALREADY_EXISTS_ON_CREATE("A resident with this email already exists."),
    EMAIL_ALREADY_EXISTS_ON_UPDATE("Another resident already uses this email.");

    private final String defaultMessage;

    @Override
    public String getCode() {
        return this.name();
    }
}
