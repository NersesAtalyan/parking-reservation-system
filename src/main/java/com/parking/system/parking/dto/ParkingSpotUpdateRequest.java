package com.parking.system.parking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to update a parking spot")
public record ParkingSpotUpdateRequest(

        @Schema(description = "Updated spot number", example = "A-102")
        @NotBlank
        String spotNumber
) {}
