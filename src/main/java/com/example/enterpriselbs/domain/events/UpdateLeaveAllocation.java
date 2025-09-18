package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;

public class UpdateLeaveAllocation implements LocalEvent {
    private final Identity staffId;
    private final int newAllocation;

    public UpdateLeaveAllocation(Identity staffId, int newAllocation) {
        this.staffId = staffId;
        this.newAllocation = newAllocation;
    }

    public Identity staffId() { return staffId; }
    public int newAllocation() { return newAllocation; }
}
