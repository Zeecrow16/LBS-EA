package com.example.enterpriselbs.infrastructure.mappers;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;


public class LeaveRequestMapper {
    public static LeaveRequestAggregate toAggregate(LeaveRequestEntity entity) {
        LeavePeriod period = new LeavePeriod(entity.getStartDate(), entity.getEndDate());

        return new LeaveRequestAggregate(
                new Identity(entity.getId()),
                new Identity(entity.getStaffId()),
                period,
                LeaveStatus.valueOf(entity.getStatus().toUpperCase())
        );
    }
    public static LeaveRequestEntity toEntity(LeaveRequestAggregate aggregate) {
        LeaveRequestEntity entity = new LeaveRequestEntity();
        entity.setId(aggregate.id().value());
        entity.setStaffId(aggregate.staffId().value());

        LeavePeriod period = aggregate.period();
        entity.setStartDate(period.startDate());
        entity.setEndDate(period.endDate());

        entity.setStatus(aggregate.status().name());
        return entity;
    }
}