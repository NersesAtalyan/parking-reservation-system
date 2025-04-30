package com.parking.system.resident.dto;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Filters for searching residents")
public record ResidentFilterRequest(
        @Schema(description = "Partial name to search") String name,
        @Schema(description = "Email to search") String email,
        @Schema(description = "Filter by active status") Boolean active,
        @Schema(description = "Filter by community ID") Long communityId
) {}