package com.parking.system.community.service;

import java.util.List;

import com.parking.system.common.exception.NotFoundException;
import com.parking.system.community.data.Community;
import com.parking.system.community.data.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityQueryService {

    private final CommunityRepository communityRepository;

    @Transactional(readOnly = true)
    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Community findByIdOrThrow(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Community.class));
    }

    @Transactional
    public Community save(Community community) {
        return communityRepository.save(community);
    }
}
