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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StaffAggregate extends BaseEntity {
    private  String username;
    private final FullName fullName;
    private Role role;
    private Identity managerId;
    private Identity departmentId;
    private int leaveAllocation;
    private Password password;
    private String descriptionOfStatus;
    private final List<LocalEvent> domainEvents = new ArrayList<>();

    StaffAggregate(Identity id,
                   String username,
                   FullName fullName,
                   Role role,
                   Identity managerId,
                   Identity departmentId,
                   int leaveAllocation,
                   Password password) {
        super(id);
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.managerId = managerId;
        this.departmentId = departmentId;
        setLeaveAllocation(leaveAllocation);
        this.password = password;
        this.descriptionOfStatus = "Active";
    }

    public String username() { return username; }
    public FullName fullName() { return fullName; }
    public Role role() { return role; }
    public Identity managerId() { return managerId; }
    public Identity departmentId() { return departmentId; }
    public int leaveAllocation() { return leaveAllocation; }
    public Password password() { return password; }
    public String descriptionOfStatus() { return descriptionOfStatus; }
    public List<LocalEvent> listOfDomainEvents() { return Collections.unmodifiableList(domainEvents); }

    public void changeRole(Role newRole) {
        if (!this.role.equals(newRole)) {
            this.role = newRole;
            addDomainEvent(new UpdateStaffRoleEvent(this.id, newRole));
        }
    }
    public void updateLeaveAllocation(int newAllocation) {
        setLeaveAllocation(newAllocation);
        addDomainEvent(new UpdateLeaveAllocation(this.id, newAllocation));
    }

    public void changeDepartment(Identity newDepartmentId) {
        String oldDept = this.departmentId != null ? this.departmentId.value() : null;
        this.departmentId = newDepartmentId;
        addDomainEvent(new UpdateDepartmentEvent(this.id.value(), oldDept, newDepartmentId.value()));
    }

    private void setLeaveAllocation(int allocation) {
        if (allocation < 0) throw new IllegalArgumentException("Leave allocation cannot be negative");
        this.leaveAllocation = allocation;
    }

    void addDomainEvent(LocalEvent event) {
        domainEvents.add(event);
    }

    public static StaffAggregate createWithEvent(Identity id,
                                                 String username,
                                                 FullName fullName,
                                                 Role role,
                                                 Identity managerId,
                                                 Identity departmentId,
                                                 int leaveAllocation,
                                                 Password password) {
        StaffAggregate staff = new StaffAggregate(id, username, fullName, role, managerId, departmentId, leaveAllocation, password);
        // example: could add a StaffCreatedEvent here
        // staff.addDomainEvent(new StaffCreatedEvent(staff));
        return staff;
    }

}

//    public void setManager(Identity newManagerId) {
//        this.managerId = newManagerId;
//    }
//
//    private void setUsername(String username) {
//        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username cannot be empty");
//    }

