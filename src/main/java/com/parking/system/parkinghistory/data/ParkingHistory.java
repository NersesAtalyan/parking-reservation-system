package com.parking.system.parkinghistory.data;

import java.time.Instant;

import com.parking.system.parking.data.ParkingSpot;
import com.parking.system.residentcommunity.data.ResidentCommunity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parking_history")
@Getter
@Setter
public class ParkingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resident_community_id", nullable = false)
    private ResidentCommunity residentCommunity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "spot_id", nullable = false)
    private ParkingSpot spot;

    @Column(name = "reserved_at")
    private Instant reservedAt;

    @Column(name = "parked_at")
    private Instant parkedAt;

    @Column(name = "released_at")
    private Instant releasedAt;
}
