package com.parking.system.community;

import java.util.List;

import com.parking.system.community.dto.CommunityCreateRequest;
import com.parking.system.community.dto.CommunityPartialUpdateRequest;
import com.parking.system.community.dto.CommunityResponse;
import com.parking.system.community.dto.CommunityUpdateRequest;
import com.parking.system.community.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
@Tag(name = "Community Management", description = "APIs for managing communities (create, update, activate, deactivate, retrieve).")
public class CommunityController {

    private final CommunityService communityService;

    @Operation(summary = "Create a new community", description = "Creates a new community with the given name, description, and address. By default, the community is active.")
    @PostMapping
    public ResponseEntity<CommunityResponse> createCommunity(@Valid @RequestBody CommunityCreateRequest request) {

        log.info("Request to create community with name: {}", request.name());
        CommunityResponse response = communityService.createCommunity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update an existing community", description = "Updates the name, description, or address of an existing community.")
    @PutMapping("/{id}")
    public ResponseEntity<CommunityResponse> updateCommunity(@Parameter(description = "ID of the community to update") @PathVariable Long id,
                                                             @Valid @RequestBody CommunityUpdateRequest request) {

        log.info("Request to update community with id: {}", id);
        CommunityResponse response = communityService.updateCommunity(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all communities", description = "Retrieves a list of all communities.")
    @GetMapping
    public ResponseEntity<List<CommunityResponse>> getAllCommunities() {

        log.info("Request to get all communities");
        List<CommunityResponse> responses = communityService.getAllCommunities();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a community by ID", description = "Retrieves the details of a community by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CommunityResponse> getCommunityById(@Parameter(description = "ID of the community to retrieve") @PathVariable Long id) {

        log.info("Request to get community by id: {}", id);
        CommunityResponse response = communityService.getCommunityById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Activate a community", description = "Sets a community's active status to true, making it available for use.")
    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activateCommunity(
            @Parameter(description = "ID of the community to activate") @PathVariable Long id) {
        log.info("Request to activate community with id: {}", id);
        communityService.activateCommunity(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deactivate a community", description = "Sets a community's active status to false, preventing it from being used.")
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCommunity(
            @Parameter(description = "ID of the community to deactivate") @PathVariable Long id) {
        log.info("Request to deactivate community with id: {}", id);
        communityService.deactivateCommunity(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Partially update a community", description = "Updates only specified fields of a community. Unspecified fields remain unchanged.")
    @PatchMapping("/{id}")
    public ResponseEntity<CommunityResponse> partialUpdateCommunity(@Parameter(description = "ID of the community to update") @PathVariable Long id,
                                                                    @RequestBody CommunityPartialUpdateRequest request) {

        log.info("Request to partially update community with id: {}", id);
        CommunityResponse response = communityService.partialUpdateCommunity(id, request);
        return ResponseEntity.ok(response);
    }
}