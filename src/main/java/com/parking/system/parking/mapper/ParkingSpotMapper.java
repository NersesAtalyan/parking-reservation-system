package com.parking.system.parking.mapper;

import com.parking.system.parking.data.ParkingSpot;
import com.parking.system.parking.dto.ParkingSpotResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkingSpotMapper {

    @Mapping(source = "community.id", target = "communityId")
    ParkingSpotResponse map(ParkingSpot spot);
}