package com.parking.system.residentcommunity.service;

import java.util.List;
import java.util.Optional;

import com.parking.system.common.exception.NotFoundException;
import com.parking.system.community.data.Community;
import com.parking.system.resident.data.Resident;
import com.parking.system.residentcommunity.data.ResidentCommunity;
import com.parking.system.residentcommunity.data.ResidentCommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResidentCommunityQueryService {

    private final ResidentCommunityRepository residentCommunityRepository;

    @Transactional(readOnly = true)
    public List<ResidentCommunity> findByResident(Resident resident) {
        return residentCommunityRepository.findByResident(resident);
    }

    @Transactional(readOnly = true)
    public List<ResidentCommunity> findByCommunity(Community community) {
        return residentCommunityRepository.findByCommunity(community);
    }

    @Transactional(readOnly = true)
    public Optional<ResidentCommunity> findByResidentAndCommunity(Resident resident, Community community) {
        return residentCommunityRepository.findByResidentAndCommunity(resident, community);
    }

    @Transactional(readOnly = true)
    public ResidentCommunity findByIdOrThrow(Long id) {
        return residentCommunityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resident-Community membership not found"));
    }

    @Transactional
    public ResidentCommunity save(ResidentCommunity residentCommunity) {
        return residentCommunityRepository.save(residentCommunity);
    }

    @Transactional(readOnly = true)
    public Optional<ResidentCommunity> findByResidentIdAndCommunityId(Long residentId, Long communityId) {
        return residentCommunityRepository.findByResidentIdAndCommunityId(residentId, communityId);
    }
}
