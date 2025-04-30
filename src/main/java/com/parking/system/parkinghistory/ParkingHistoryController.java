package com.parking.system.parkinghistory;

import java.util.List;

import com.parking.system.parkinghistory.dto.ParkingHistoryResponse;
import com.parking.system.parkinghistory.service.ParkingHistoryReaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Parking History", description = "APIs for viewing parking history.")
public class ParkingHistoryController {

    private final ParkingHistoryReaderService parkingHistoryReaderService;

    @Operation(summary = "Get parking history for a parking spot")
    @GetMapping("/parking-spots/{spotId}/history")
    public ResponseEntity<List<ParkingHistoryResponse>> getHistoryBySpot(@Parameter(description = "Parking Spot ID") @PathVariable Long spotId) {

        log.info("Request to get parking history for spot {}", spotId);
        List<ParkingHistoryResponse> response = parkingHistoryReaderService.getHistoryBySpot(spotId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get parking history for a resident")
    @GetMapping("/residents/{residentId}/history")
    public ResponseEntity<List<ParkingHistoryResponse>> getHistoryByResident(@Parameter(description = "Resident ID") @PathVariable Long residentId) {

        log.info("Request to get parking history for resident {}", residentId);
        List<ParkingHistoryResponse> response = parkingHistoryReaderService.getHistoryByResident(residentId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get parking history for a community")
    @GetMapping("/communities/{communityId}/history")
    public ResponseEntity<List<ParkingHistoryResponse>> getHistoryByCommunity(@Parameter(description = "Community ID") @PathVariable Long communityId) {

        log.info("Request to get parking history for community {}", communityId);
        List<ParkingHistoryResponse> response = parkingHistoryReaderService.getHistoryByCommunity(communityId);
        return ResponseEntity.ok(response);
    }
}