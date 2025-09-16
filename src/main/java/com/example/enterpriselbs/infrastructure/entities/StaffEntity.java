package com.example.enterpriselbs.infrastructure.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "staff")
public class StaffEntity {

    @Id
    @Column(name = "staff_id")
    private String id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String surname;

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

    public StaffEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

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