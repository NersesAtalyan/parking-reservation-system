package com.parking.system.parkinghistory.mapper;

import com.parking.system.parkinghistory.data.ParkingHistory;
import com.parking.system.parkinghistory.dto.ParkingHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkingHistoryMapper {

    @Mapping(source = "spot.id", target = "spotId")
    @Mapping(source = "spot.spotNumber", target = "spotNumber")
    @Mapping(source = "residentCommunity.id", target = "residentCommunityId")
    @Mapping(source = "residentCommunity.resident.name", target = "residentName")
    @Mapping(source = "residentCommunity.community.name", target = "communityName")
    ParkingHistoryResponse map(ParkingHistory entity);
}