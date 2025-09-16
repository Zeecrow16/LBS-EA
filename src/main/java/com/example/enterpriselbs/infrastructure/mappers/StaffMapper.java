package com.example.enterpriselbs.infrastructure.mappers;

import com.example.enterpriselbs.application.dto.StaffDto;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.domain.valueObjects.FullName;
import com.example.enterpriselbs.infrastructure.entities.StaffEntity;


public class StaffMapper {

    public static StaffAggregate toAggregate(StaffEntity e) {
        return new StaffAggregate(
                new Identity(e.getId()),
                e.getUsername(),
                new FullName(e.getFirstName(), e.getSurname()),
                e.getRole(),
                e.getManagerId() != null ? new Identity(e.getManagerId()) : null,
                e.getDepartmentId() != null ? new Identity(e.getDepartmentId()) : null,
                e.getLeaveAllocation(),
                e.getPasswordHash()
        );
    }

    public static StaffEntity toEntity(StaffAggregate a) {
        StaffEntity e = new StaffEntity();
        e.setId(a.id().value());
        e.setUsername(a.username());
        e.setFirstName(a.fullName().firstName());
        e.setSurname(a.fullName().surname());
        e.setRole(a.role());
        e.setManagerId(a.managerId() != null ? a.managerId().value() : null);
        e.setDepartmentId(a.departmentId() != null ? a.departmentId().value() : null);
        e.setLeaveAllocation(a.leaveAllocation());
        e.setPasswordHash(a.password());
        return e;
    }
}