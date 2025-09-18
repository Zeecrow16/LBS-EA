package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;

import java.time.LocalDate;

public class LeaveRejectedEvent implements LocalEvent {
    private final LeaveRequestAggregate leaveRequest;

    public LeaveRejectedEvent(LeaveRequestAggregate leaveRequest) {
        this.leaveRequest = leaveRequest;
    }

    public LeaveRequestAggregate leaveRequest() { return leaveRequest; }
}