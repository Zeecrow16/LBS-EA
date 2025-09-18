package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;

import java.time.LocalDate;

public class LeaveCancelledEvent implements LocalEvent {
    private final LeaveRequestAggregate leaveRequest;

    public LeaveCancelledEvent(LeaveRequestAggregate leaveRequest) {
        this.leaveRequest = leaveRequest;
    }

    public LeaveRequestAggregate leaveRequest() { return leaveRequest; }
}