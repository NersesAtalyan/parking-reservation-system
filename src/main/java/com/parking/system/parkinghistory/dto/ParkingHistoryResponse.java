package com.parking.system.parkinghistory.dto;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response representing a parking history record")
public record ParkingHistoryResponse(

        @Schema(description = "Parking history ID", example = "1")
        Long id,

        @Schema(description = "Parking spot ID", example = "5")
        Long spotId,

        @Schema(description = "Parking spot number", example = "A-01")
        String spotNumber,

        @Schema(description = "ResidentCommunity ID", example = "2")
        Long residentCommunityId,

        @Schema(description = "Resident name", example = "John Doe")
        String residentName,

        @Schema(description = "Community name", example = "Green Hills Residence")
        String communityName,

        @Schema(description = "Time of reservation")
        Instant reservedAt,

        @Schema(description = "Time of parking")
        Instant parkedAt,

        @Schema(description = "Time of release")
        Instant releasedAt
) {}

