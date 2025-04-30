package com.parking.system.resident.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to update an existing resident")
public record ResidentUpdateRequest(

        @Schema(description = "Updated name of the resident", example = "John Doe Updated")
        @NotBlank
        String name,

        @Schema(description = "Updated email of the resident", example = "john.updated@example.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "Updated phone number of the resident", example = "+1-555-555-1234")
        String phoneNumber
) {}