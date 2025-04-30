package com.parking.system.residentcommunity.data;

import java.util.List;
import java.util.Optional;

import com.parking.system.community.data.Community;
import com.parking.system.resident.data.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentCommunityRepository extends JpaRepository<ResidentCommunity, Long> {

    Optional<ResidentCommunity> findByResidentAndCommunity(Resident resident, Community community);

    List<ResidentCommunity> findByResident(Resident resident);

    List<ResidentCommunity> findByCommunity(Community community);

    Optional<ResidentCommunity> findByResidentIdAndCommunityId(Long residentId, Long communityId);

}