package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@ToString
public class StaffAddedEvent implements LocalEvent {
    private String occurredOn;
    private StaffAggregate staff;

    public StaffAddedEvent(StaffAggregate staff) {
        this.occurredOn = LocalDate.now().toString();
        this.staff = staff;
    }
}
//public class StaffAddedEvent implements LocalEvent {
//    private final String staffId;
//    private final String username;
//
//    public StaffAddedEvent(StaffAggregate staff) {
//        this.staffId = staff.id().value();
//        this.username = staff.username();
//    }
//
//    public String staffId() { return staffId; }
//    public String username() { return username; }
//}