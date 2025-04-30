package com.parking.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@ConfigurationPropertiesScan("com.parking.system.common.properties")
@Slf4j
@SpringBootApplication
public class ParkingReservationSystemApplication {

    public static void main(String[] args) {
        log.info("🚗 Starting Parking Reservation System...");
        SpringApplication.run(ParkingReservationSystemApplication.class, args);
        log.info("✅ Parking Reservation System started successfully.");
    }
}
