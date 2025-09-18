package com.example.enterpriselbs.domain.aggregates.factory;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.domain.events.StaffAddedEvent;
import com.example.enterpriselbs.domain.valueObjects.FullName;
import com.example.enterpriselbs.domain.valueObjects.Password;
import com.example.enterpriselbs.domain.valueObjects.Role;
import org.springframework.stereotype.Component;

@Component
public class StaffFactory {

    public StaffAggregate createNewStaff(
            String username,
            FullName fullName,
            Role role,
            Identity managerId,
            Identity departmentId,
            int leaveAllocation,
            Password password
    ) {
        StaffAggregate staff = new StaffAggregate(
                new Identity(java.util.UUID.randomUUID().toString()),
                username,
                fullName,
                role,
                managerId,
                departmentId,
                leaveAllocation,
                password
        );

        staff.addDomainEvent(new StaffAddedEvent(staff));
        return staff;
    }
}