package com.parking.system.resident.service;

import java.util.List;

import com.parking.system.common.exception.NotFoundException;
import com.parking.system.resident.data.Resident;
import com.parking.system.resident.data.ResidentRepository;
import com.parking.system.resident.dto.ResidentFilterRequest;
import com.parking.system.resident.filter.ResidentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResidentQueryService {

    private final ResidentRepository residentRepository;

    @Transactional(readOnly = true)
    public Page<Resident> search(ResidentFilterRequest filter, Pageable pageable) {
        return residentRepository.findAll(ResidentSpecification.byFilter(filter), pageable);
    }

    @Transactional(readOnly = true)
    public List<Resident> findAll() {
        return residentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Resident findByIdOrThrow(Long id) {
        return residentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Resident.class));
    }

    @Transactional
    public Resident save(Resident resident) {
        return residentRepository.save(resident);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return residentRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return residentRepository.existsByEmailAndIdNot(email, id);
    }
}
