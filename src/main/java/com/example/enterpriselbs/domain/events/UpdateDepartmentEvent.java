package com.example.enterpriselbs.domain.events;

import org.springframework.data.domain.DomainEvents;

public class UpdateDepartmentEvent implements LocalEvent {
    private final String staffId;
    private final String oldDepartmentId;
    private final String newDepartmentId;

    public UpdateDepartmentEvent(String staffId, String oldDepartmentId, String newDepartmentId) {
        this.staffId = staffId;
        this.oldDepartmentId = oldDepartmentId;
        this.newDepartmentId = newDepartmentId;
    }

    public String staffId() { return staffId; }
    public String oldDepartmentId() { return oldDepartmentId; }
    public String newDepartmentId() { return newDepartmentId; }
}
