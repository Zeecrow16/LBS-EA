package com.example.enterpriselbs.application.commands;

import com.example.enterpriselbs.domain.valueObjects.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AmendRoleCommand {
    private String staffId;
    private Role newRole;
}
