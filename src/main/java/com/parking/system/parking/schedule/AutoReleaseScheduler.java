package com.parking.system.parking.schedule;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import com.parking.system.common.properties.ParkingProperties;
import com.parking.system.notification.NotifierService;
import com.parking.system.parking.data.ParkingSpot;
import com.parking.system.parking.presets.SpotStatus;
import com.parking.system.parking.service.ParkingSpotQueryService;
import com.parking.system.parkinghistory.service.ParkingHistoryManagerService;
import com.parking.system.residentcommunity.data.ResidentCommunity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutoReleaseScheduler {

    private final ParkingProperties parkingProperties;
    private final ParkingSpotQueryService parkingSpotQueryService;
    private final ParkingHistoryManagerService parkingHistoryManagerService;
    private final NotifierService notifierService;

    @Scheduled(cron = "0 */10 * * * *")
    @Transactional
    public void releaseExpiredReservations() {
        log.info("Auto-releasing expired parking spots...");

        Duration timeout = parkingProperties.autoReleaseTimeout();
        Instant expirationTime = Instant.now().minus(timeout);

        List<ParkingSpot> expiredSpots = parkingSpotQueryService.findReservedBefore(expirationTime);

        for (ParkingSpot spot : expiredSpots) {
            log.info("Auto-releasing spot ID={}, number='{}', community='{}' (reserved at: {})",
                    spot.getId(), spot.getSpotNumber(), spot.getCommunity().getName(), spot.getReservedAt());

            ResidentCommunity reservedBy = spot.getReservedBy();
            if (reservedBy != null && reservedBy.getResident() != null) {
                notifierService.notifyAutoRelease(reservedBy.getResident(), spot.getSpotNumber(), spot.getCommunity().getName());
            }

            spot.setStatus(SpotStatus.AVAILABLE);
            spot.setReservedAt(null);
            spot.setReservedBy(null);
            spot.setParkedAt(null);

            parkingSpotQueryService.save(spot);
            parkingHistoryManagerService.recordRelease(spot);
        }

        if (!expiredSpots.isEmpty()) {
            log.info("Auto-release complete: {} spot(s) released", expiredSpots.size());
        }
    }
}
