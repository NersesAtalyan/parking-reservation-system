package com.parking.system.community.mapper;

import com.parking.system.community.data.Community;
import com.parking.system.community.dto.CommunityCreateRequest;
import com.parking.system.community.dto.CommunityPartialUpdateRequest;
import com.parking.system.community.dto.CommunityResponse;
import com.parking.system.community.dto.CommunityUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommunityMapper {

    Community map(CommunityCreateRequest request);

    void map(@MappingTarget Community community, CommunityUpdateRequest request);

    CommunityResponse map(Community community);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void map(@MappingTarget Community community, CommunityPartialUpdateRequest request);

}
