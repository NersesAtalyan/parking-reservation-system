package com.parking.system.residentcommunity.service;

import com.parking.system.common.exception.BusinessException;
import com.parking.system.community.data.Community;
import com.parking.system.community.service.CommunityQueryService;
import com.parking.system.resident.data.Resident;
import com.parking.system.resident.service.ResidentQueryService;
import com.parking.system.residentcommunity.data.ResidentCommunity;
import com.parking.system.residentcommunity.dto.ResidentCommunityResponse;
import com.parking.system.residentcommunity.mapper.ResidentCommunityMapper;
import com.parking.system.residentcommunity.presets.ResidentCommunityErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResidentCommunityService {

    private final ResidentQueryService residentQueryService;
    private final CommunityQueryService communityQueryService;
    private final ResidentCommunityQueryService residentCommunityQueryService;
    private final ResidentCommunityMapper residentCommunityMapper;

    @Transactional
    public void joinCommunity(Long residentId, Long communityId) {
        Resident resident = residentQueryService.findByIdOrThrow(residentId);
        Community community = communityQueryService.findByIdOrThrow(communityId);

        ResidentCommunity existing = residentCommunityQueryService.findByResidentAndCommunity(resident, community)
                .orElse(null);

        if (existing != null && existing.isActive()) {
            throw new BusinessException(ResidentCommunityErrorCodes.ALREADY_MEMBER);
        }

        if (existing != null) {
            existing.setActive(true);
            residentCommunityQueryService.save(existing);
        } else {
            ResidentCommunity newMembership = new ResidentCommunity();
            newMembership.setResident(resident);
            newMembership.setCommunity(community);
            newMembership.setActive(true);
            residentCommunityQueryService.save(newMembership);
        }
    }

    @Transactional
    public void leaveCommunity(Long residentId, Long communityId) {
        Resident resident = residentQueryService.findByIdOrThrow(residentId);
        Community community = communityQueryService.findByIdOrThrow(communityId);

        ResidentCommunity membership = residentCommunityQueryService.findByResidentAndCommunity(resident, community)
                .orElseThrow(() -> new BusinessException(ResidentCommunityErrorCodes.MEMBERSHIP_NOT_FOUND));

        if (!membership.isActive()) {
            throw new BusinessException(ResidentCommunityErrorCodes.ALREADY_LEFT);
        }

        membership.setActive(false);
        residentCommunityQueryService.save(membership);
    }

    @Transactional(readOnly = true)
    public List<ResidentCommunityResponse> getResidentCommunities(Long residentId) {
        Resident resident = residentQueryService.findByIdOrThrow(residentId);
        return residentCommunityQueryService.findByResident(resident).stream()
                .map(residentCommunityMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ResidentCommunityResponse> getCommunityResidents(Long communityId) {
        Community community = communityQueryService.findByIdOrThrow(communityId);
        return residentCommunityQueryService.findByCommunity(community).stream()
                .map(residentCommunityMapper::toResponse)
                .toList();
    }
}
