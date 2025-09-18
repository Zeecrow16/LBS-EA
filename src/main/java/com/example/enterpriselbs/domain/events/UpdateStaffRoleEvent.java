package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.valueObjects.Role;

public class UpdateStaffRoleEvent implements LocalEvent {
    private final Identity staffId;
    private final Role newRole;

    public UpdateStaffRoleEvent(Identity staffId, Role newRole) {
        this.staffId = staffId;
        this.newRole = newRole;
    }

    public Identity staffId() { return staffId; }
    public Role newRole() { return newRole; }
}