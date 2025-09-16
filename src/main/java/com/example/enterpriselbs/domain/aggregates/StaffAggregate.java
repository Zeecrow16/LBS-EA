package com.example.enterpriselbs.domain.aggregates;

import com.example.enterpriselbs.domain.BaseEntity;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.valueObjects.FullName;

import java.util.Objects;


public class StaffAggregate extends BaseEntity {
    private final String username;
    private final FullName fullName;
    private String role;
    private Identity managerId;
    private Identity departmentId;
    private int leaveAllocation;
    private String password;

    public StaffAggregate(Identity id, String username, FullName fullName, String role,
                          Identity managerId, Identity departmentId, int leaveAllocation, String password) {
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
    public String role() { return role; }
    public Identity managerId() { return managerId; }
    public Identity departmentId() { return departmentId; }
    public int leaveAllocation() { return leaveAllocation; }
    public String password() { return password; }

    public String getId() { return id.value(); }
    public String getUsername() { return username; }
    public String getFirstName() { return fullName.firstName(); }
    public String getSurname() { return fullName.surname(); }
    public String getRole() { return role; }
    public String getManagerId() { return managerId != null ? managerId.value() : null; }
    public String getDepartmentId() { return departmentId != null ? departmentId.value() : null; }
    public int getLeaveAllocation() { return leaveAllocation; }
    public String getPassword() { return password; }

    public void changeRole(String newRole) {
        if (newRole == null || newRole.isBlank()) throw new IllegalArgumentException("Role cannot be empty");
        this.role = newRole;
    }

    public void updateLeaveAllocation(int newAllocation) {
        if (newAllocation < 0) throw new IllegalArgumentException("Leave allocation cannot be negative");
        this.leaveAllocation = newAllocation;
    }

    public void changePassword(String newPassword) {
        if (newPassword == null || newPassword.isBlank()) throw new IllegalArgumentException("Password cannot be empty");
        this.password = newPassword;
    }

    public void setManagerId(Identity managerId) { this.managerId = managerId; }
    public void setDepartmentId(Identity departmentId) { this.departmentId = departmentId; }

}