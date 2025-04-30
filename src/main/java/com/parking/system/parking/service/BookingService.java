package com.parking.system.parking.service;

import java.time.Instant;

import com.parking.system.common.exception.BusinessException;
import com.parking.system.community.data.Community;
import com.parking.system.notification.NotifierService;
import com.parking.system.parking.data.ParkingSpot;
import com.parking.system.parking.presets.ParkingErrorCodes;
import com.parking.system.parking.presets.SpotStatus;
import com.parking.system.parkinghistory.service.ParkingHistoryManagerService;
import com.parking.system.residentcommunity.data.ResidentCommunity;
import com.parking.system.residentcommunity.presets.ResidentCommunityErrorCodes;
import com.parking.system.residentcommunity.service.ResidentCommunityQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ParkingSpotQueryService parkingSpotQueryService;
    private final ResidentCommunityQueryService residentCommunityQueryService;
    private final ParkingHistoryManagerService parkingHistoryManagerService;
    private final NotifierService notifierService;

    @Transactional
    public void bookSpot(Long spotId, Long residentId) {
        ParkingSpot spot = parkingSpotQueryService.findByIdOrThrow(spotId);

        if (!SpotStatus.AVAILABLE.equals(spot.getStatus())) {
            throw new BusinessException(ParkingErrorCodes.SPOT_NOT_AVAILABLE);
        }

        ResidentCommunity residentCommunity = findResidentCommunityOrThrow(residentId, spot.getCommunity());

        spot.setStatus(SpotStatus.RESERVED);
        spot.setReservedBy(residentCommunity);
        spot.setReservedAt(Instant.now());
        parkingSpotQueryService.save(spot);

        parkingHistoryManagerService.createBookingHistory(spot, residentCommunity);
    }

    @Transactional
    public void parkAtSpot(Long spotId, Long residentId) {
        ParkingSpot spot = parkingSpotQueryService.findByIdOrThrow(spotId);

        if (!SpotStatus.RESERVED.equals(spot.getStatus())) {
            throw new BusinessException(ParkingErrorCodes.SPOT_NOT_RESERVED);
        }

        ResidentCommunity residentCommunity = findResidentCommunityOrThrow(residentId, spot.getCommunity());

        if (!residentCommunity.equals(spot.getReservedBy())) {
            throw new BusinessException(ParkingErrorCodes.NOT_YOUR_RESERVATION);
        }

        spot.setStatus(SpotStatus.OCCUPIED);
        spot.setParkedAt(Instant.now());
        parkingSpotQueryService.save(spot);

        parkingHistoryManagerService.recordParking(spot);
    }

    @Transactional
    public void releaseSpot(Long spotId, Long residentId) {
        ParkingSpot spot = parkingSpotQueryService.findByIdOrThrow(spotId);

        if (SpotStatus.AVAILABLE.equals(spot.getStatus())) {
            throw new BusinessException(ParkingErrorCodes.SPOT_ALREADY_AVAILABLE);
        }

        ResidentCommunity residentCommunity = findResidentCommunityOrThrow(residentId, spot.getCommunity());

        if (!residentCommunity.equals(spot.getReservedBy())) {
            throw new BusinessException(ParkingErrorCodes.NOT_YOUR_SPOT);
        }

        spot.setStatus(SpotStatus.AVAILABLE);
        spot.setReservedBy(null);
        spot.setReservedAt(null);
        spot.setParkedAt(null);
        parkingSpotQueryService.save(spot);

        parkingHistoryManagerService.recordRelease(spot);
    }

    private ResidentCommunity findResidentCommunityOrThrow(Long residentId, Community community) {
        ResidentCommunity membership = residentCommunityQueryService
                .findByResidentIdAndCommunityId(residentId, community.getId())
                .orElseThrow(() -> new BusinessException(ResidentCommunityErrorCodes.MEMBERSHIP_NOT_FOUND));

        if (!membership.isActive()) {
            throw new BusinessException(ResidentCommunityErrorCodes.INACTIVE_MEMBERSHIP);
        }

        if (!membership.getResident().isActive()) {
            throw new BusinessException(ResidentCommunityErrorCodes.INACTIVE_RESIDENT);
        }

        return membership;
    }

    @Transactional
    public void forceRelease(ParkingSpot spot) {
        parkingHistoryManagerService.recordRelease(spot);

        if (spot.getReservedBy() != null && spot.getReservedBy().getResident() != null) {
            notifierService.notifyAutoRelease(spot.getReservedBy().getResident(), spot.getSpotNumber(), spot.getCommunity().getName());
        }

        spot.setStatus(SpotStatus.AVAILABLE);
        spot.setReservedAt(null);
        spot.setReservedBy(null);
        spot.setParkedAt(null);

        parkingSpotQueryService.save(spot);
    }
}
