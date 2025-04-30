package com.parking.system.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to create a new community")
public record CommunityCreateRequest(

        @Schema(description = "Name of the community", example = "Green Hills Residence")
        @NotBlank
        String name,

        @Schema(description = "Description of the community", example = "Luxury apartments near the park")
        String description,

        @Schema(description = "Address of the community", example = "123 Park Avenue, New York")
        String address,

        @Schema(description = "Phone number of the community", example = "+1-123-456-7890")
        String phoneNumber
) {}
