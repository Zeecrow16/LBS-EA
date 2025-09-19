package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class LeaveCancelledEvent implements LocalEvent {
    private String occurredOn;
    private LeaveRequestAggregate leaveRequest;

    public LeaveCancelledEvent(LeaveRequestAggregate leaveRequest) {
        this.occurredOn = LocalDate.now().toString();
        this.leaveRequest = leaveRequest;
    }
}

//public class LeaveCancelledEvent implements LocalEvent {
//    private final LeaveRequestAggregate leaveRequest;
//
//    public LeaveCancelledEvent(LeaveRequestAggregate leaveRequest) {
//        this.leaveRequest = leaveRequest;
//    }
//
//    public LeaveRequestAggregate leaveRequest() { return leaveRequest; }
//}