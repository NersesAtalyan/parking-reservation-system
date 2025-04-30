package com.parking.system.parking;

import java.util.List;

import com.parking.system.parking.dto.ParkingSpotCreateRequest;
import com.parking.system.parking.dto.ParkingSpotResponse;
import com.parking.system.parking.dto.ParkingSpotUpdateRequest;
import com.parking.system.parking.service.ParkingSpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Parking Spot Management", description = "APIs for managing parking spots (create, update, retrieve).")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @Operation(summary = "Create a new parking spot in a community")
    @PostMapping("/communities/{communityId}/parking-spots")
    public ResponseEntity<ParkingSpotResponse> createParkingSpot(@Parameter(description = "Community ID") @PathVariable Long communityId,
                                                                 @Valid @RequestBody ParkingSpotCreateRequest request) {

        log.info("Request to create parking spot in community {}", communityId);
        ParkingSpotResponse response = parkingSpotService.createParkingSpot(communityId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update an existing parking spot")
    @PutMapping("/parking-spots/{spotId}")
    public ResponseEntity<ParkingSpotResponse> updateParkingSpot(@Parameter(description = "Parking Spot ID") @PathVariable Long spotId,
                                                                 @Valid @RequestBody ParkingSpotUpdateRequest request) {

        log.info("Request to update parking spot with id {}", spotId);
        ParkingSpotResponse response = parkingSpotService.updateParkingSpot(spotId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all parking spots for a community")
    @GetMapping("/communities/{communityId}/parking-spots")
    public ResponseEntity<List<ParkingSpotResponse>> getParkingSpotsByCommunity(@Parameter(description = "Community ID") @PathVariable Long communityId) {

        log.info("Request to get parking spots for community {}", communityId);
        List<ParkingSpotResponse> responses = parkingSpotService.getParkingSpotsByCommunity(communityId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a parking spot by ID")
    @GetMapping("/parking-spots/{spotId}")
    public ResponseEntity<ParkingSpotResponse> getParkingSpotById(@Parameter(description = "Parking Spot ID") @PathVariable Long spotId) {

        log.info("Request to get parking spot with id {}", spotId);
        ParkingSpotResponse response = parkingSpotService.getParkingSpotById(spotId);
        return ResponseEntity.ok(response);
    }
}