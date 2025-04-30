package com.parking.system.residentcommunity.mapper;

import com.parking.system.residentcommunity.data.ResidentCommunity;
import com.parking.system.residentcommunity.dto.ResidentCommunityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResidentCommunityMapper {

    @Mapping(source = "resident.id", target = "residentId")
    @Mapping(source = "resident.name", target = "residentName")
    @Mapping(source = "community.id", target = "communityId")
    @Mapping(source = "community.name", target = "communityName")
    ResidentCommunityResponse toResponse(ResidentCommunity residentCommunity);

}
