package com.example.enterpriselbs.application.commands;

import com.example.enterpriselbs.domain.valueObjects.Password;
import com.example.enterpriselbs.domain.valueObjects.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddStaffCommand {
    private String username;
    private String firstName;
    private String surname;
    private Role role;
    private String managerId;
    private String departmentId;
    private int leaveAllocation;
    private String password;
}
