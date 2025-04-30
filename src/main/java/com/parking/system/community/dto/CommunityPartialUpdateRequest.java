package com.parking.system.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to partially update a community")
public record CommunityPartialUpdateRequest(

        @Schema(description = "New name of the community", example = "Partial Update Name")
        String name,

        @Schema(description = "New description", example = "Partial updated description")
        String description,

        @Schema(description = "New address", example = "789 Another Address")
        String address,

        @Schema(description = "New phone number", example = "+1-222-333-4444")
        String phoneNumber
) {}

