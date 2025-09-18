package com.example.enterpriselbs.domain.aggregates;

import com.example.enterpriselbs.domain.BaseEntity;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.events.LocalEvent;
import com.example.enterpriselbs.domain.events.UpdateDepartmentEvent;
import com.example.enterpriselbs.domain.events.UpdateLeaveAllocation;
import com.example.enterpriselbs.domain.events.UpdateStaffRoleEvent;
import com.example.enterpriselbs.domain.valueObjects.FullName;
import com.example.enterpriselbs.domain.valueObjects.Password;
import com.example.enterpriselbs.domain.valueObjects.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffAggregate extends BaseEntity {
    private final String username;
    private final FullName fullName;
    private Role role;
    private Identity managerId;
    private Identity departmentId;
    private int leaveAllocation;
    private Password password;

    private final List<LocalEvent> domainEvents = new ArrayList<>();

    public StaffAggregate(
            Identity id,
            String username,
            FullName fullName,
            Role role,
            Identity managerId,
            Identity departmentId,
            int leaveAllocation,
            Password password
    ) {
        super(id);
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.managerId = managerId;
        this.departmentId = departmentId;
        this.leaveAllocation = leaveAllocation;
        this.password = password;
    }

    public Identity id() { return id; }
    public String username() { return username; }
    public FullName fullName() { return fullName; }
    public Role role() { return role; }
    public Identity managerId() { return managerId; }
    public Identity departmentId() { return departmentId; }
    public int leaveAllocation() { return leaveAllocation; }
    public Password password() { return password; }

    public List<LocalEvent> listOfDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    public void addDomainEvent(LocalEvent event) {
        domainEvents.add(event);
    }

    public void changeRole(Role newRole) {
        if (!this.role.equals(newRole)) {
            this.role = newRole;
            addDomainEvent(new UpdateStaffRoleEvent(this.id, newRole));
        }
    }
    public void updateLeaveAllocation(int newAllocation) {
        if (newAllocation < 0) throw new IllegalArgumentException("Leave allocation cannot be negative");
        if (this.leaveAllocation != newAllocation) {
            this.leaveAllocation = newAllocation;
            addDomainEvent(new UpdateLeaveAllocation(this.id, newAllocation));
        }
    }

    public void changeDepartment(Identity newDepartmentId) {
        String oldDept = this.departmentId != null ? this.departmentId.value() : null;
        this.departmentId = newDepartmentId;
        addDomainEvent(new UpdateDepartmentEvent(this.id.value(), oldDept, newDepartmentId.value()));
    }

    public void setManagerId(Identity managerId) { this.managerId = managerId; }
    public void setDepartmentId(Identity departmentId) { this.departmentId = departmentId; }
}
