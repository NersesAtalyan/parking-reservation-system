package com.parking.system.community;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.system.common.exception.BusinessException;
import com.parking.system.common.exception.CommonErrorCodes;
import com.parking.system.common.exception.NotFoundException;
import com.parking.system.community.dto.CommunityCreateRequest;
import com.parking.system.community.dto.CommunityResponse;
import com.parking.system.community.dto.CommunityUpdateRequest;
import com.parking.system.community.service.CommunityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommunityController.class)
class CommunityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommunityService communityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create community successfully")
    void createCommunityTest() throws Exception {
        CommunityCreateRequest request = new CommunityCreateRequest("Test Community", "Description", "Address", "+1-123-456-7890");
        CommunityResponse response = new CommunityResponse(1L, "Test Community", "Description", "Address", "+1-123-456-7890", true);

        Mockito.when(communityService.createCommunity(any(CommunityCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/communities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Community")))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    @DisplayName("Should return 400 when BusinessException thrown")
    void createCommunity_BusinessExceptionTest() throws Exception {
        CommunityCreateRequest request = new CommunityCreateRequest("Test Community", "Description", "Address", "+1-123-456-7890");

        Mockito.when(communityService.createCommunity(any(CommunityCreateRequest.class)))
                .thenThrow(new BusinessException(CommonErrorCodes.ALREADY_ACTIVE, "Community is already active"));

        mockMvc.perform(post("/api/communities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is("Business Rule Violation")))
                .andExpect(jsonPath("$.errorCode", is("ALREADY_ACTIVE")));
    }

    @Test
    @DisplayName("Should update community successfully")
    void updateCommunityTest() throws Exception {
        CommunityUpdateRequest request = new CommunityUpdateRequest("Updated Name", "Updated Desc", "Updated Address", "+1-123-456-7890");
        CommunityResponse response = new CommunityResponse(1L, "Updated Name", "Updated Desc", "Updated Address", "+1-123-456-7890", true);

        Mockito.when(communityService.updateCommunity(eq(1L), any(CommunityUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/communities/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Name")))
                .andExpect(jsonPath("$.address", is("Updated Address")));
    }

    @Test
    @DisplayName("Should return all communities")
    void getAllCommunitiesTest() throws Exception {
        List<CommunityResponse> responses = List.of(
                new CommunityResponse(1L, "Community A", "Desc A", "Addr A", "+1-123-456-7890", true),
                new CommunityResponse(2L, "Community B", "Desc B", "Addr B", "+1-123-456-5555", false)
        );

        Mockito.when(communityService.getAllCommunities()).thenReturn(responses);

        mockMvc.perform(get("/api/communities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    @DisplayName("Should return community by ID")
    void getCommunityByIdTest() throws Exception {
        CommunityResponse response = new CommunityResponse(1L, "Community A", "Desc A", "Addr A", "+1-123-456-7890", true);

        Mockito.when(communityService.getCommunityById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/communities/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Community A")));
    }

    @Test
    @DisplayName("Should return 404 when community not found")
    void getCommunityById_NotFoundTest() throws Exception {
        Mockito.when(communityService.getCommunityById(1L))
                .thenThrow(new NotFoundException("Community not found"));

        mockMvc.perform(get("/api/communities/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", is("Resource Not Found")))
                .andExpect(jsonPath("$.detail", is("Community not found")));
    }

    @Test
    @DisplayName("Should activate community")
    void activateCommunityTest() throws Exception {
        mockMvc.perform(post("/api/communities/{id}/activate", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should deactivate community")
    void deactivateCommunityTest() throws Exception {
        mockMvc.perform(post("/api/communities/{id}/deactivate", 1L))
                .andExpect(status().isNoContent());
    }
}