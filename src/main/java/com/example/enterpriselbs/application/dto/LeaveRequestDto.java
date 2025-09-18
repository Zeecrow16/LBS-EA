package com.example.enterpriselbs.application.dto;

import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;

import java.time.LocalDate;

public class LeaveRequestDto {
    public String staffId;
    public LocalDate startDate;
    public LocalDate endDate;
    public LeaveStatus status;
}