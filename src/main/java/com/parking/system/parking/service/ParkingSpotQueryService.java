package com.parking.system.parking.service;

import java.time.Instant;
import java.util.List;

import com.parking.system.common.exception.NotFoundException;
import com.parking.system.community.data.Community;
import com.parking.system.parking.data.ParkingSpot;
import com.parking.system.parking.data.ParkingSpotRepository;
import com.parking.system.parking.presets.SpotStatus;
import com.parking.system.residentcommunity.data.ResidentCommunity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingSpotQueryService {

    private final ParkingSpotRepository parkingSpotRepository;

    @Transactional(readOnly = true)
    public ParkingSpot findByIdOrThrow(Long id) {
        return parkingSpotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ParkingSpot.class));
    }

    @Transactional(readOnly = true)
    public List<ParkingSpot> findByCommunity(Community community) {
        return parkingSpotRepository.findByCommunity(community);
    }

    @Transactional
    public ParkingSpot save(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    public List<ParkingSpot> findReservedBefore(Instant time) {
        return parkingSpotRepository.findByStatusAndReservedAtBefore(SpotStatus.RESERVED, time);
    }

    @Transactional(readOnly = true)
    public List<ParkingSpot> findAllReservedByResidentCommunity(ResidentCommunity rc) {
        return parkingSpotRepository.findAllByReservedBy(rc);
    }
}
