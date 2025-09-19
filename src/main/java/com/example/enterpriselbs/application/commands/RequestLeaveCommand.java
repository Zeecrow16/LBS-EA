package com.example.enterpriselbs.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RequestLeaveCommand {
    private String staffId;
    private LocalDate startDate;
    private LocalDate endDate;
}