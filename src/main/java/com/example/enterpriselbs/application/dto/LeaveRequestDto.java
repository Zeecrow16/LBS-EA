package com.example.enterpriselbs.application.dto;

import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Getter
@Setter
@ToString
public class LeaveRequestDto {
    public String id;
    public String staffId;
    public LocalDate startDate;
    public LocalDate endDate;
    public LeaveStatus status;
}