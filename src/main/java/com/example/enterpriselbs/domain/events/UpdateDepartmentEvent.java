package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.DomainEvents;

import java.time.LocalDate;

@Getter
@ToString
public class UpdateDepartmentEvent implements LocalEvent {
    private String occurredOn;
    private StaffAggregate staff;

    public UpdateDepartmentEvent(StaffAggregate staff) {
        this.occurredOn = LocalDate.now().toString();
        this.staff = staff;
    }
}
//public class UpdateDepartmentEvent implements LocalEvent {
//    private final String staffId;
//    private final String oldDepartmentId;
//    private final String newDepartmentId;
//
//    public UpdateDepartmentEvent(String staffId, String oldDepartmentId, String newDepartmentId) {
//        this.staffId = staffId;
//        this.oldDepartmentId = oldDepartmentId;
//        this.newDepartmentId = newDepartmentId;
//    }
//
//    public String staffId() { return staffId; }
//    public String oldDepartmentId() { return oldDepartmentId; }
//    public String newDepartmentId() { return newDepartmentId; }
//}
