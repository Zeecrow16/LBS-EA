package com.example.enterpriselbs.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RejectLeaveCommand {
    private String leaveRequestId;
}
