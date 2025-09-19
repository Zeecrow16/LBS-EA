package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class LeaveApprovedEvent implements LocalEvent {
    private String occurredOn;
    private LeaveRequestAggregate leaveRequest;

    public LeaveApprovedEvent(LeaveRequestAggregate leaveRequest) {
        this.occurredOn = LocalDate.now().toString();
        this.leaveRequest = leaveRequest;
    }
}
//public class LeaveApprovedEvent implements LocalEvent {
//    private final LeaveRequestAggregate leaveRequest;
//
//    public LeaveApprovedEvent(LeaveRequestAggregate leaveRequest) {
//        this.leaveRequest = leaveRequest;
//    }
//
//    public LeaveRequestAggregate leaveRequest() { return leaveRequest; }
//}