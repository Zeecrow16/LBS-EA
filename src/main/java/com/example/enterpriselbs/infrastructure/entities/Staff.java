package com.example.enterpriselbs.infrastructure.entities;

import com.example.enterpriselbs.infrastructure.constants.StaffConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @Column(name = "staff_id")
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "manager_id")
    private String managerId;

    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "leave_allocation")
    private int leaveAllocation;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public int getLeaveAllocation() { return leaveAllocation; }
    public void setLeaveAllocation(int leaveAllocation) { this.leaveAllocation = leaveAllocation; }
}