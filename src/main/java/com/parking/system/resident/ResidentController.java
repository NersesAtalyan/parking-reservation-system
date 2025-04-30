package com.parking.system.resident;

import java.util.List;

import com.parking.system.resident.dto.ResidentCreateRequest;
import com.parking.system.resident.dto.ResidentResponse;
import com.parking.system.resident.dto.ResidentFilterRequest;
import com.parking.system.resident.dto.ResidentUpdateRequest;
import com.parking.system.resident.service.ResidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/residents")
@RequiredArgsConstructor
@Tag(name = "Resident Management", description = "APIs for managing residents (create, update, activate, deactivate, retrieve).")
public class ResidentController {

    private final ResidentService residentService;

    @Operation(summary = "Search residents", description = "Search residents by filters with pagination.")
    @GetMapping
    public Page<ResidentResponse> search(@ParameterObject ResidentFilterRequest residentFilterRequest,
                                         @ParameterObject @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("Request to search residents: {}", residentFilterRequest);
        return residentService.search(residentFilterRequest, pageable);
    }

    @Operation(summary = "Create a new resident", description = "Creates a new resident with name, email, and phone number. By default, the resident is active.")
    @PostMapping
    public ResponseEntity<ResidentResponse> createResident(@Valid @RequestBody ResidentCreateRequest request) {
        log.info("Request to create resident with name: {}", request.name());
        ResidentResponse response = residentService.createResident(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update an existing resident", description = "Updates the name, email, or phone number of an existing resident.")
    @PutMapping("/{id}")
    public ResponseEntity<ResidentResponse> updateResident(
            @Parameter(description = "ID of the resident to update") @PathVariable Long id,
            @Valid @RequestBody ResidentUpdateRequest request) {

        log.info("Request to update resident with id: {}", id);
        ResidentResponse response = residentService.updateResident(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all residents", description = "Retrieves a list of all residents.")
    @GetMapping("/all")
    public ResponseEntity<List<ResidentResponse>> getAllResidents() {
        log.info("Request to get all residents");
        List<ResidentResponse> responses = residentService.listResidents();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a resident by ID", description = "Retrieves the details of a resident by their ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResidentResponse> getResidentById(
            @Parameter(description = "ID of the resident to retrieve") @PathVariable Long id) {

        log.info("Request to get resident by id: {}", id);
        ResidentResponse response = residentService.getResidentById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Activate a resident", description = "Sets a resident's active status to true, making them active.")
    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activateResident(
            @Parameter(description = "ID of the resident to activate") @PathVariable Long id) {

        log.info("Request to activate resident with id: {}", id);
        residentService.activateResident(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deactivate a resident", description = "Sets a resident's active status to false, making them inactive.")
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateResident(
            @Parameter(description = "ID of the resident to deactivate") @PathVariable Long id) {

        log.info("Request to deactivate resident with id: {}", id);
        residentService.deactivateResident(id);
        return ResponseEntity.noContent().build();
    }
}