package com.parking.system.parkinghistory.service;

import java.time.Instant;

import com.parking.system.parking.data.ParkingSpot;
import com.parking.system.parkinghistory.data.ParkingHistory;
import com.parking.system.parkinghistory.data.ParkingHistoryRepository;
import com.parking.system.residentcommunity.data.ResidentCommunity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingHistoryManagerService {

    private final ParkingHistoryRepository parkingHistoryRepository;

    @Transactional
    public void createBookingHistory(ParkingSpot spot, ResidentCommunity residentCommunity) {
        ParkingHistory history = new ParkingHistory();
        history.setSpot(spot);
        history.setResidentCommunity(residentCommunity);
        history.setReservedAt(Instant.now());
        parkingHistoryRepository.save(history);
    }

    @Transactional
    public void recordParking(ParkingSpot spot) {
        ParkingHistory history = parkingHistoryRepository.findActiveHistoryBySpotId(spot.getId())
                .orElseThrow(() -> new IllegalStateException("No active booking found to park."));
        history.setParkedAt(Instant.now());
        parkingHistoryRepository.save(history);
    }

    @Transactional
    public void recordRelease(ParkingSpot spot) {
        ParkingHistory history = parkingHistoryRepository.findActiveHistoryBySpotId(spot.getId())
                .orElseThrow(() -> new IllegalStateException("No active booking found to release."));
        history.setReleasedAt(Instant.now());
        parkingHistoryRepository.save(history);
    }
}
