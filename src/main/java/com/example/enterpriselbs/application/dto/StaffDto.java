package com.example.enterpriselbs.application.dto;


import com.example.enterpriselbs.domain.valueObjects.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StaffDto {
    public String id;
    public String username;
    public String firstName;
    public String surname;
    public String passwordHash;
    public Role role;
    public String managerId;
    public String departmentId;
    public int leaveAllocation;
}