package com.parking.system.resident.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to create a new resident")
public record ResidentCreateRequest(

        @Schema(description = "Name of the resident", example = "Roger Waters")
        @NotBlank
        String name,

        @Schema(description = "Email of the resident", example = "roger.waters@example.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "Phone number of the resident", example = "+1-555-555-5555")
        String phoneNumber
) {}