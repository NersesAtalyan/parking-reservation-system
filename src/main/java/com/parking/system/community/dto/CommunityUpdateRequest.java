package com.parking.system.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to update an existing community")
public record CommunityUpdateRequest(

        @Schema(description = "Name of the community", example = "Updated Community Name")
        @NotBlank
        String name,

        @Schema(description = "Description of the community", example = "Updated description")
        String description,

        @Schema(description = "Address of the community", example = "456 New Address Street")
        String address,

        @Schema(description = "Phone number of the community", example = "+1-987-654-3210")
        String phoneNumber
) {}

