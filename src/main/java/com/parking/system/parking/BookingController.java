package com.parking.system.parking;

import com.parking.system.parking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/parking-spots")
@RequiredArgsConstructor
@Tag(name = "Parking Reservation", description = "APIs for booking, parking, and releasing parking spots.")
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Book a parking spot")
    @PostMapping("/{spotId}/book")
    public ResponseEntity<Void> bookSpot(@Parameter(description = "Parking Spot ID") @PathVariable Long spotId,
                                         @Parameter(description = "Resident ID") @RequestParam Long residentId) {

        log.info("Resident {} is booking parking spot {}", residentId, spotId);
        bookingService.bookSpot(spotId, residentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Park at a reserved parking spot")
    @PostMapping("/{spotId}/park")
    public ResponseEntity<Void> parkAtSpot(@Parameter(description = "Parking Spot ID") @PathVariable Long spotId,
                                           @Parameter(description = "Resident ID") @RequestParam Long residentId) {

        log.info("Resident {} is parking at parking spot {}", residentId, spotId);
        bookingService.parkAtSpot(spotId, residentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Release a parking spot")
    @PostMapping("/{spotId}/release")
    public ResponseEntity<Void> releaseSpot(@Parameter(description = "Parking Spot ID") @PathVariable Long spotId,
                                            @Parameter(description = "Resident ID") @RequestParam Long residentId) {

        log.info("Resident {} is releasing parking spot {}", residentId, spotId);
        bookingService.releaseSpot(spotId, residentId);
        return ResponseEntity.noContent().build();
    }
}