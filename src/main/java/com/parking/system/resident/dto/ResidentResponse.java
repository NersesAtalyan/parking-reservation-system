package com.parking.system.resident.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response representing a resident")
public record ResidentResponse(

        @Schema(description = "Unique identifier of the resident", example = "1")
        Long id,

        @Schema(description = "Name of the resident", example = "John Doe")
        String name,

        @Schema(description = "Email of the resident", example = "john.doe@example.com")
        String email,

        @Schema(description = "Phone number of the resident", example = "+1-555-555-5555")
        String phoneNumber,

        @Schema(description = "Whether the resident is active", example = "true")
        boolean active
) {}