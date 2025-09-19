package com.example.enterpriselbs.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AmendDepartmentCommand {
    private String staffId;
    private String newDepartmentId;
}
