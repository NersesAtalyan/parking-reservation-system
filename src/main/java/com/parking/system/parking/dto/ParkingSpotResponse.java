package com.parking.system.parking.dto;

import com.parking.system.parking.presets.SpotStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response representing a parking spot")
public record ParkingSpotResponse(

        @Schema(description = "Unique identifier of the parking spot", example = "1")
        Long id,

        @Schema(description = "Spot number", example = "A-101")
        String spotNumber,

        @Schema(description = "Status of the parking spot", example = "AVAILABLE")
        SpotStatus status,

        @Schema(description = "Community ID", example = "1")
        Long communityId
) {}
