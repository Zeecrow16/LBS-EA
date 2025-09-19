package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.valueObjects.Role;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class UpdateStaffRoleEvent implements LocalEvent {
    private String occurredOn;
    private Identity staffId;
    private Role newRole;

    public UpdateStaffRoleEvent(Identity staffId, Role newRole) {
        this.occurredOn = LocalDate.now().toString();
        this.staffId = staffId;
        this.newRole = newRole;
    }
}
//
//public class UpdateStaffRoleEvent implements LocalEvent {
//    private final Identity staffId;
//    private final Role newRole;
//
//    public UpdateStaffRoleEvent(Identity staffId, Role newRole) {
//        this.staffId = staffId;
//        this.newRole = newRole;
//    }
//
//    public Identity staffId() { return staffId; }
//    public Role newRole() { return newRole; }
//}