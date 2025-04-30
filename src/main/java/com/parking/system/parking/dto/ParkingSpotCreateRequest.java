package com.parking.system.parking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to create a new parking spot")
public record ParkingSpotCreateRequest(

        @Schema(description = "Spot number", example = "A-101")
        @NotBlank
        String spotNumber
) {}
