package com.parking.system.parkinghistory.service;

import java.util.List;

import com.parking.system.parkinghistory.data.ParkingHistoryRepository;
import com.parking.system.parkinghistory.dto.ParkingHistoryResponse;
import com.parking.system.parkinghistory.mapper.ParkingHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingHistoryReaderService {

    private final ParkingHistoryRepository parkingHistoryRepository;
    private final ParkingHistoryMapper parkingHistoryMapper;

    @Transactional(readOnly = true)
    public List<ParkingHistoryResponse> getHistoryBySpot(Long spotId) {
        return parkingHistoryRepository.findBySpotId(spotId).stream()
                .map(parkingHistoryMapper::map)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ParkingHistoryResponse> getHistoryByResident(Long residentId) {
        return parkingHistoryRepository.findByResidentCommunityResidentId(residentId).stream()
                .map(parkingHistoryMapper::map)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ParkingHistoryResponse> getHistoryByCommunity(Long communityId) {
        return parkingHistoryRepository.findByResidentCommunityCommunityId(communityId).stream()
                .map(parkingHistoryMapper::map)
                .toList();
    }
}
