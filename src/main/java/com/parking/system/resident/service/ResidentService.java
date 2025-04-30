package com.parking.system.resident.service;

import java.util.List;

import com.parking.system.common.exception.BusinessException;
import com.parking.system.resident.data.Resident;
import com.parking.system.resident.dto.ResidentCreateRequest;
import com.parking.system.resident.dto.ResidentResponse;
import com.parking.system.resident.dto.ResidentFilterRequest;
import com.parking.system.resident.dto.ResidentUpdateRequest;
import com.parking.system.resident.mapper.ResidentMapper;
import com.parking.system.resident.presets.ResidentErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResidentService {

    private final ResidentQueryService residentQueryService;
    private final ResidentMapper residentMapper;

    @Transactional
    public ResidentResponse createResident(ResidentCreateRequest request) {
        if (residentQueryService.existsByEmail(request.email())) {
            throw new BusinessException(ResidentErrorCodes.EMAIL_ALREADY_EXISTS_ON_CREATE);
        }
        Resident resident = residentMapper.map(request);
        resident.setActive(true);
        resident = residentQueryService.save(resident);
        return residentMapper.map(resident);
    }

    @Transactional
    public ResidentResponse updateResident(Long id, ResidentUpdateRequest request) {
        if (residentQueryService.existsByEmailAndIdNot(request.email(), id)) {
            throw new BusinessException(ResidentErrorCodes.EMAIL_ALREADY_EXISTS_ON_UPDATE);
        }
        Resident resident = residentQueryService.findByIdOrThrow(id);
        residentMapper.map(resident, request);
        resident = residentQueryService.save(resident);
        return residentMapper.map(resident);
    }

    @Transactional
    public void activateResident(Long id) {
        Resident resident = residentQueryService.findByIdOrThrow(id);
        resident.setActive(true);
        residentQueryService.save(resident);
    }

    @Transactional
    public void deactivateResident(Long id) {
        Resident resident = residentQueryService.findByIdOrThrow(id);
        resident.setActive(false);
        residentQueryService.save(resident);
    }

    @Transactional(readOnly = true)
    public ResidentResponse getResidentById(Long id) {
        Resident resident = residentQueryService.findByIdOrThrow(id);
        return residentMapper.map(resident);
    }

    @Transactional(readOnly = true)
    public List<ResidentResponse> listResidents() {
        return residentQueryService.findAll().stream()
                .map(residentMapper::map)
                .toList();
    }

    public Page<ResidentResponse> search(ResidentFilterRequest filterRequest, Pageable pageable) {
        return residentQueryService.search(filterRequest, pageable)
                .map(residentMapper::map );
    }
}

