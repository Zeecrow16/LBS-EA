package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class UpdateLeaveAllocation implements LocalEvent {
    private String occurredOn;
    private Identity staffId;
    private int newAllocation;

    public UpdateLeaveAllocation(Identity staffId, int newAllocation) {
        this.occurredOn = LocalDate.now().toString();
        this.staffId = staffId;
        this.newAllocation = newAllocation;
    }
}
//public class UpdateLeaveAllocation implements LocalEvent {
//    private final Identity staffId;
//    private final int newAllocation;
//
//    public UpdateLeaveAllocation(Identity staffId, int newAllocation) {
//        this.staffId = staffId;
//        this.newAllocation = newAllocation;
//    }
//
//    public Identity staffId() { return staffId; }
//    public int newAllocation() { return newAllocation; }
//}
