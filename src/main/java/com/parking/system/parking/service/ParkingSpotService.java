package com.parking.system.parking.service;

import com.parking.system.community.data.Community;
import com.parking.system.community.service.CommunityQueryService;
import com.parking.system.parking.data.ParkingSpot;
import com.parking.system.parking.dto.ParkingSpotCreateRequest;
import com.parking.system.parking.dto.ParkingSpotResponse;
import com.parking.system.parking.dto.ParkingSpotUpdateRequest;
import com.parking.system.parking.mapper.ParkingSpotMapper;
import com.parking.system.parking.presets.SpotStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotQueryService parkingSpotQueryService;
    private final CommunityQueryService communityQueryService;
    private final ParkingSpotMapper parkingSpotMapper;

    @Transactional
    public ParkingSpotResponse createParkingSpot(Long communityId, ParkingSpotCreateRequest request) {
        Community community = communityQueryService.findByIdOrThrow(communityId);

        ParkingSpot spot = new ParkingSpot();
        spot.setCommunity(community);
        spot.setSpotNumber(request.spotNumber());
        spot.setStatus(SpotStatus.AVAILABLE);
        spot = parkingSpotQueryService.save(spot);

        return parkingSpotMapper.map(spot);
    }

    @Transactional
    public ParkingSpotResponse updateParkingSpot(Long spotId, ParkingSpotUpdateRequest request) {
        ParkingSpot spot = parkingSpotQueryService.findByIdOrThrow(spotId);
        spot.setSpotNumber(request.spotNumber());
        spot = parkingSpotQueryService.save(spot);

        return parkingSpotMapper.map(spot);
    }

    @Transactional(readOnly = true)
    public List<ParkingSpotResponse> getParkingSpotsByCommunity(Long communityId) {
        Community community = communityQueryService.findByIdOrThrow(communityId);
        return parkingSpotQueryService.findByCommunity(community).stream()
                .map(parkingSpotMapper::map)
                .toList();
    }

    @Transactional(readOnly = true)
    public ParkingSpotResponse getParkingSpotById(Long spotId) {
        ParkingSpot spot = parkingSpotQueryService.findByIdOrThrow(spotId);
        return parkingSpotMapper.map(spot);
    }



}
