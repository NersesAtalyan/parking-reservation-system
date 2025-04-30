package com.parking.system.parking.data;

import java.time.Instant;
import java.util.List;

import com.parking.system.community.data.Community;
import com.parking.system.parking.presets.SpotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    List<ParkingSpot> findByCommunity(Community community);

    List<ParkingSpot> findByStatusAndReservedAtBefore(SpotStatus spotStatus, Instant time);
}