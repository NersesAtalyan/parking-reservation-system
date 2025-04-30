package com.parking.system.community.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.parking.system.common.exception.BusinessException;
import com.parking.system.community.data.Community;
import com.parking.system.community.dto.CommunityCreateRequest;
import com.parking.system.community.dto.CommunityPartialUpdateRequest;
import com.parking.system.community.dto.CommunityResponse;
import com.parking.system.community.dto.CommunityUpdateRequest;
import com.parking.system.community.mapper.CommunityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommunityServiceTest {

    @Mock
    private CommunityQueryService communityQueryService;

    @Mock
    private CommunityMapper communityMapper;

    @InjectMocks
    private CommunityService communityService;

    @Test
    @DisplayName("Should create community successfully")
    void createCommunityTest() {
        CommunityCreateRequest request = new CommunityCreateRequest("Test", "desc", "address", "+1-123-456-7890");
        Community entity = new Community();
        Community savedEntity = new Community();
        CommunityResponse response = new CommunityResponse(1L, "Test", "desc", "address", "+1-123-456-7890", true);

        when(communityMapper.map(request)).thenReturn(entity);
        when(communityQueryService.save(entity)).thenReturn(savedEntity);
        when(communityMapper.map(savedEntity)).thenReturn(response);

        CommunityResponse result = communityService.createCommunity(request);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Test");

        verify(communityQueryService).save(entity);
    }

    @Test
    @DisplayName("Should update community successfully")
    void updateCommunityTest() {
        CommunityUpdateRequest request = new CommunityUpdateRequest("Updated", "desc", "address", "+1-123-456-7890");
        Community entity = new Community();
        Community updatedEntity = new Community();
        CommunityResponse response = new CommunityResponse(1L, "Updated", "desc", "address", "+1-123-456-7890", true);

        when(communityQueryService.findByIdOrThrow(1L)).thenReturn(entity);
        doNothing().when(communityMapper).map(entity, request);
        when(communityQueryService.save(entity)).thenReturn(updatedEntity);
        when(communityMapper.map(updatedEntity)).thenReturn(response);

        CommunityResponse result = communityService.updateCommunity(1L, request);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Updated");

        verify(communityQueryService).save(entity);
    }

    @Test
    @DisplayName("Should get all communities")
    void getAllCommunitiesTest() {
        List<Community> entities = List.of(new Community(), new Community());
        when(communityQueryService.findAll()).thenReturn(entities);
        when(communityMapper.map(any(Community.class))).thenReturn(new CommunityResponse(1L, "Test", "desc", "addr", "+1-123-456-7890", true));

        List<CommunityResponse> responses = communityService.getAllCommunities();

        assertThat(responses).hasSize(2);
    }

    @Test
    @DisplayName("Should get community by ID")
    void getCommunityByIdTest() {
        Community entity = new Community();
        CommunityResponse response = new CommunityResponse(1L, "Test", "desc", "addr", "+1-123-456-7890", true);

        when(communityQueryService.findByIdOrThrow(1L)).thenReturn(entity);
        when(communityMapper.map(entity)).thenReturn(response);

        CommunityResponse result = communityService.getCommunityById(1L);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Test");
    }

    @Test
    @DisplayName("Should activate community successfully")
    void activateCommunityTest() {
        Community community = new Community();
        community.setActive(false);

        when(communityQueryService.findByIdOrThrow(1L)).thenReturn(community);
        when(communityQueryService.save(community)).thenReturn(community);

        communityService.activateCommunity(1L);

        assertThat(community.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should throw BusinessException when activating already active community")
    void activateCommunityAlreadyActiveTest() {
        Community community = new Community();
        community.setActive(true);

        when(communityQueryService.findByIdOrThrow(1L)).thenReturn(community);

        assertThatThrownBy(() -> communityService.activateCommunity(1L))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("Should deactivate community successfully")
    void deactivateCommunityTest() {
        Community community = new Community();
        community.setActive(true);

        when(communityQueryService.findByIdOrThrow(1L)).thenReturn(community);
        when(communityQueryService.save(community)).thenReturn(community);

        communityService.deactivateCommunity(1L);

        assertThat(community.isActive()).isFalse();
    }

    @Test
    @DisplayName("Should throw BusinessException when deactivating already deactivated community")
    void deactivateCommunityAlreadyDeactivatedTest() {
        Community community = new Community();
        community.setActive(false);

        when(communityQueryService.findByIdOrThrow(1L)).thenReturn(community);

        assertThatThrownBy(() -> communityService.deactivateCommunity(1L))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("Should partial update community successfully")
    void partialUpdateCommunityTest() {
        Community community = new Community();
        CommunityPartialUpdateRequest request = new CommunityPartialUpdateRequest("partial", null, null, null);
        Community updatedEntity = new Community();
        CommunityResponse response = new CommunityResponse(1L, "partial", null, null, null, true);

        when(communityQueryService.findByIdOrThrow(1L)).thenReturn(community);
        doNothing().when(communityMapper).map(community, request);
        when(communityQueryService.save(community)).thenReturn(updatedEntity);
        when(communityMapper.map(updatedEntity)).thenReturn(response);

        CommunityResponse result = communityService.partialUpdateCommunity(1L, request);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("partial");
    }
}