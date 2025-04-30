package com.parking.system.community.service;

import static com.parking.system.common.exception.CommonErrorCodes.ALREADY_ACTIVE;
import static com.parking.system.common.exception.CommonErrorCodes.ALREADY_DEACTIVATED;

import java.util.List;

import com.parking.system.common.exception.BusinessException;
import com.parking.system.community.data.Community;
import com.parking.system.community.dto.CommunityCreateRequest;
import com.parking.system.community.dto.CommunityPartialUpdateRequest;
import com.parking.system.community.dto.CommunityResponse;
import com.parking.system.community.dto.CommunityUpdateRequest;
import com.parking.system.community.mapper.CommunityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityQueryService communityQueryService;
    private final CommunityMapper communityMapper;

    @Transactional
    public CommunityResponse createCommunity(CommunityCreateRequest request) {
        Community community = communityMapper.map(request);
        Community saved = communityQueryService.save(community);
        return communityMapper.map(saved);
    }

    @Transactional
    public CommunityResponse updateCommunity(Long id, CommunityUpdateRequest request) {
        Community community = communityQueryService.findByIdOrThrow(id);
        communityMapper.map(community, request);
        Community updated = communityQueryService.save(community);
        return communityMapper.map(updated);
    }

    @Transactional(readOnly = true)
    public List<CommunityResponse> getAllCommunities() {
        return communityQueryService.findAll().stream()
                .map(communityMapper::map)
                .toList();
    }

    @Transactional(readOnly = true)
    public CommunityResponse getCommunityById(Long id) {
        Community community = communityQueryService.findByIdOrThrow(id);
        return communityMapper.map(community);
    }

    @Transactional
    public void activateCommunity(Long id) {
        Community community = communityQueryService.findByIdOrThrow(id);
        if (community.isActive()) {
            throw new BusinessException(ALREADY_ACTIVE, "Community '" + community.getName() + "' is already active");
        }
        community.setActive(true);
        communityQueryService.save(community);
    }

    @Transactional
    public void deactivateCommunity(Long id) {
        Community community = communityQueryService.findByIdOrThrow(id);
        if (!community.isActive()) {
            throw new BusinessException(ALREADY_DEACTIVATED, "Community '" + community.getName() + "' is already deactivated");
        }
        community.setActive(false);
        communityQueryService.save(community);
    }

    @Transactional
    public CommunityResponse partialUpdateCommunity(Long id, CommunityPartialUpdateRequest request) {
        Community community = communityQueryService.findByIdOrThrow(id);
        communityMapper.map(community, request);
        Community updated = communityQueryService.save(community);
        return communityMapper.map(updated);
    }
}

