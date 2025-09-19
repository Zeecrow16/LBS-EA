package com.example.enterpriselbs.infrastructure.mappers;

import com.example.enterpriselbs.application.dto.StaffDto;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.domain.valueObjects.FullName;
import com.example.enterpriselbs.domain.valueObjects.Password;
import com.example.enterpriselbs.domain.valueObjects.Role;
import com.example.enterpriselbs.infrastructure.entities.StaffEntity;

import java.util.List;
import java.util.stream.Collectors;

public class StaffMapper {
    public static StaffAggregate toDomain(StaffEntity entity) {
        return StaffAggregate.createWithEvent(
                new Identity(entity.getId()),
                entity.getUsername(),
                new FullName(entity.getFirstName(), entity.getSurname()),
                Role.valueOf(entity.getRole().toUpperCase()),
                entity.getManagerId() != null ? new Identity(entity.getManagerId()) : null,
                entity.getDepartmentId() != null ? new Identity(entity.getDepartmentId()) : null,
                entity.getLeaveAllocation(),
                new Password(entity.getPasswordHash())
        );
    }

    public static StaffEntity toJpa(StaffAggregate aggregate) {
        StaffEntity entity = new StaffEntity();
        entity.setId(aggregate.id().value());
        entity.setUsername(aggregate.username());
        entity.setFirstName(aggregate.fullName().firstName());
        entity.setSurname(aggregate.fullName().surname());
        entity.setRole(aggregate.role().name());
        entity.setManagerId(aggregate.managerId() != null ? aggregate.managerId().value() : null);
        entity.setDepartmentId(aggregate.departmentId() != null ? aggregate.departmentId().value() : null);
        entity.setLeaveAllocation(aggregate.leaveAllocation());
        entity.setPasswordHash(aggregate.password().value());
        return entity;
    }

    public static StaffDto toDTO(StaffEntity entity) {
        StaffDto dto = new StaffDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setFirstName(entity.getFirstName());
        dto.setSurname(entity.getSurname());
        dto.setRole(Role.valueOf(entity.getRole().toUpperCase()));
        dto.setManagerId(entity.getManagerId());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setLeaveAllocation(entity.getLeaveAllocation());
        return dto;
    }

    public static List<StaffDto> toDTOList(List<StaffEntity> entities) {
        return entities.stream()
                .map(StaffMapper::toDTO)
                .collect(Collectors.toList());
    }
}
