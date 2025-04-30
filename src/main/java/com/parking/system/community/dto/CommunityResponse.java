package com.parking.system.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response representing a community")
public record CommunityResponse(

        @Schema(description = "Unique identifier of the community", example = "1")
        Long id,

        @Schema(description = "Name of the community", example = "Green Hills Residence")
        String name,

        @Schema(description = "Description of the community", example = "Luxury apartments near the park")
        String description,

        @Schema(description = "Address of the community", example = "123 Park Avenue, New York")
        String address,

        @Schema(description = "Phone number of the community", example = "+1-123-456-7890")
        String phoneNumber,

        @Schema(description = "Whether the community is active", example = "true")
        boolean active
) {}
