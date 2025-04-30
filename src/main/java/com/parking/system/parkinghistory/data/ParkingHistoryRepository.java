package com.parking.system.parkinghistory.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingHistoryRepository extends JpaRepository<ParkingHistory, Long> {

    @Query("SELECT h FROM ParkingHistory h WHERE h.spot.id = :spotId AND h.releasedAt IS NULL ORDER BY h.reservedAt DESC")
    Optional<ParkingHistory> findActiveHistoryBySpotId(@Param("spotId") Long spotId);

    List<ParkingHistory> findBySpotId(Long spotId);

    List<ParkingHistory> findByResidentCommunityResidentId(Long residentId);

    List<ParkingHistory> findByResidentCommunityCommunityId(Long communityId);
}