package com.parking.system.residentcommunity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response representing a Resident's membership in a Community")
public record ResidentCommunityResponse(

        @Schema(description = "Resident-Community membership ID", example = "1")
        Long id,

        @Schema(description = "Resident ID", example = "1")
        Long residentId,

        @Schema(description = "Resident name", example = "John Doe")
        String residentName,

        @Schema(description = "Community ID", example = "3")
        Long communityId,

        @Schema(description = "Community name", example = "Green Hills Residence")
        String communityName,

        @Schema(description = "Whether membership is active", example = "true")
        boolean active
) {}