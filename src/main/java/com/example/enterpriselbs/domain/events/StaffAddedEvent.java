package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.aggregates.StaffAggregate;

public class StaffAddedEvent implements LocalEvent {
    private final String staffId;
    private final String username;

    public StaffAddedEvent(StaffAggregate staff) {
        this.staffId = staff.id().value();
        this.username = staff.username();
    }

    public String staffId() { return staffId; }
    public String username() { return username; }
}