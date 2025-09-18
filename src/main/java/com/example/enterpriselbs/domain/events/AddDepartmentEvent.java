package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.domain.aggregates.DepartmentAggregate;

public class AddDepartmentEvent implements LocalEvent {
    private final DepartmentAggregate department;

    public AddDepartmentEvent(DepartmentAggregate department) {
        this.department = department;
    }

    public DepartmentAggregate department() { return department; }

    @Override
    public String toString() {
        return "AddDepartmentEvent{departmentId=" + department.id().value() + "}";
    }
}