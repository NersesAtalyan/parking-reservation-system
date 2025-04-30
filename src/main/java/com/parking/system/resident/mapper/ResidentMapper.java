package com.parking.system.resident.mapper;

import com.parking.system.resident.data.Resident;
import com.parking.system.resident.dto.ResidentCreateRequest;
import com.parking.system.resident.dto.ResidentResponse;
import com.parking.system.resident.dto.ResidentUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResidentMapper {

    Resident map(ResidentCreateRequest request);

    void map(@MappingTarget Resident resident, ResidentUpdateRequest request);

    ResidentResponse map(Resident resident);
}
