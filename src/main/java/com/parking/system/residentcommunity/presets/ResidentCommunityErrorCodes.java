package com.parking.system.residentcommunity.presets;

import com.parking.system.common.exception.BusinessErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResidentCommunityErrorCodes implements BusinessErrorCode {
    ALREADY_MEMBER("Resident is already an active member of this community."),
    ALREADY_LEFT("Resident already left this community."),
    MEMBERSHIP_NOT_FOUND("Resident is not a member of this community."),
    INACTIVE_MEMBERSHIP("Resident is no longer active in the community."),
    INACTIVE_RESIDENT("Resident account is deactivated.");

    private final String defaultMessage;

    @Override
    public String getCode() {
        return this.name();
    }
}
