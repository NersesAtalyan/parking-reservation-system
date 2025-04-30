package com.parking.system.parking.presets;

import com.parking.system.common.exception.BusinessErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParkingErrorCodes implements BusinessErrorCode {
    SPOT_NOT_AVAILABLE("Parking spot is not available for booking."),
    SPOT_NOT_RESERVED("Parking spot must be reserved before parking."),
    SPOT_ALREADY_AVAILABLE("Parking spot is already available."),
    NOT_YOUR_RESERVATION("You cannot park at a spot reserved by another resident."),
    NOT_YOUR_SPOT("You cannot release a spot reserved by another resident.");

    private final String defaultMessage;

    @Override
    public String getCode() {
        return this.name();
    }
}
