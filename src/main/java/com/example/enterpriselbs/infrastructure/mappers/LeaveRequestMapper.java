package com.example.enterpriselbs.infrastructure.mappers;

import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;

import java.util.List;
import java.util.stream.Collectors;

public class LeaveRequestMapper {

    public static LeaveRequestAggregate toDomain(LeaveRequestEntity entity) {
        LeavePeriod period = new LeavePeriod(entity.getStartDate(), entity.getEndDate());
        return LeaveRequestAggregate.leaveRequestOf(
                new Identity(entity.getId()),
                new Identity(entity.getStaffId()),
                period,
                LeaveStatus.valueOf(entity.getStatus().toUpperCase()),
                "Loaded from persistence"
        );
    }

    public static LeaveRequestEntity toJpa(LeaveRequestAggregate aggregate) {
        LeaveRequestEntity entity = new LeaveRequestEntity();
        entity.setId(aggregate.id().value());
        entity.setStaffId(aggregate.staffId().value());
        entity.setStartDate(aggregate.period().startDate());
        entity.setEndDate(aggregate.period().endDate());
        entity.setStatus(aggregate.status().name());
        return entity;
    }

    public static LeaveRequestDto toDTO(LeaveRequestEntity entity) {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(entity.getId());
        dto.setStaffId(entity.getStaffId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setStatus(LeaveStatus.valueOf(entity.getStatus().toUpperCase()));
        return dto;
    }

    public static List<LeaveRequestDto> toDTOList(List<LeaveRequestEntity> entities) {
        return entities.stream()
                .map(LeaveRequestMapper::toDTO)
                .collect(Collectors.toList());
    }
}