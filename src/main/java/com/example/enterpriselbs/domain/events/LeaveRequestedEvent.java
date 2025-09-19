package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class LeaveRequestedEvent implements LocalEvent {
    private String occurredOn;
    private LeaveRequestAggregate leaveRequest;

    public LeaveRequestedEvent(LeaveRequestAggregate leaveRequest) {
        this.occurredOn = LocalDate.now().toString();
        this.leaveRequest = leaveRequest;
    }
}
//public class LeaveRequestedEvent implements LocalEvent {
//    private final LeaveRequestAggregate leaveRequest;
//
//    public LeaveRequestedEvent(LeaveRequestAggregate leaveRequest) {
//        this.leaveRequest = leaveRequest;
//    }
//
//    public LeaveRequestAggregate leaveRequest() { return leaveRequest; }
//}