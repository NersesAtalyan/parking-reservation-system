package com.parking.system.residentcommunity;

import com.parking.system.residentcommunity.dto.ResidentCommunityResponse;
import com.parking.system.residentcommunity.service.ResidentCommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Resident-Community Management", description = "APIs for managing resident memberships in communities (join, leave, list).")
public class ResidentCommunityController {

    private final ResidentCommunityService residentCommunityService;

    @Operation(summary = "Resident joins a community")
    @PostMapping("/residents/{residentId}/communities/{communityId}/join")
    public ResponseEntity<Void> joinCommunity(@Parameter(description = "Resident ID") @PathVariable Long residentId,
                                              @Parameter(description = "Community ID") @PathVariable Long communityId) {

        log.info("Request for resident {} to join community {}", residentId, communityId);
        residentCommunityService.joinCommunity(residentId, communityId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Resident leaves a community")
    @PostMapping("/residents/{residentId}/communities/{communityId}/leave")
    public ResponseEntity<Void> leaveCommunity(@Parameter(description = "Resident ID") @PathVariable Long residentId,
                                               @Parameter(description = "Community ID") @PathVariable Long communityId) {

        log.info("Request for resident {} to leave community {}", residentId, communityId);
        residentCommunityService.leaveCommunity(residentId, communityId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List communities for a resident")
    @GetMapping("/residents/{residentId}/communities")
    public ResponseEntity<List<ResidentCommunityResponse>> getResidentCommunities(@Parameter(description = "Resident ID") @PathVariable Long residentId) {

        log.info("Request to list communities for resident {}", residentId);
        List<ResidentCommunityResponse> responses = residentCommunityService.getResidentCommunities(residentId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "List residents of a community")
    @GetMapping("/communities/{communityId}/residents")
    public ResponseEntity<List<ResidentCommunityResponse>> getCommunityResidents(@Parameter(description = "Community ID") @PathVariable Long communityId) {

        log.info("Request to list residents for community {}", communityId);
        List<ResidentCommunityResponse> responses = residentCommunityService.getCommunityResidents(communityId);
        return ResponseEntity.ok(responses);
    }
}