package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;

import java.time.LocalDate;

public class LeaveCancelledEvent implements LocalEvent {
    private final String occurredOn;
    private final String leaveRequestId;
    private final String staffId;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public LeaveCancelledEvent(LeaveRequestAggregate leaveRequest) {
        this.occurredOn = LocalDate.now().toString();
        this.leaveRequestId = leaveRequest.id().value();
        this.staffId = leaveRequest.staffId().value();
        this.startDate = leaveRequest.period().startDate();
        this.endDate = leaveRequest.period().endDate();
    }

    public String occurredOn() { return occurredOn; }
    public String leaveRequestId() { return leaveRequestId; }
    public String staffId() { return staffId; }
    public LocalDate startDate() { return startDate; }
    public LocalDate endDate() { return endDate; }
}
