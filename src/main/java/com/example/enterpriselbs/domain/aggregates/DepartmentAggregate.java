package com.example.enterpriselbs.domain.aggregates;

import com.example.enterpriselbs.domain.BaseEntity;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.events.AddDepartmentEvent;
import com.example.enterpriselbs.domain.events.LocalEvent;
import com.example.enterpriselbs.domain.valueObjects.DepartmentName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DepartmentAggregate extends BaseEntity {

    private DepartmentName name;
    private final List<LocalEvent> domainEvents = new ArrayList<>();

    public DepartmentAggregate(Identity id, DepartmentName name) {
        super(id);
        this.name = Objects.requireNonNull(name, "Department name is required");
    }

    public DepartmentName name() { return name; }

    public void changeName(DepartmentName newName) {
        this.name = Objects.requireNonNull(newName, "Department name cannot be empty");
    }

    public List<LocalEvent> listOfDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    protected void addDomainEvent(LocalEvent event) {
        domainEvents.add(event);
    }

    public static DepartmentAggregate createWithEvent(DepartmentName name) {
        DepartmentAggregate dept = new DepartmentAggregate(new Identity(java.util.UUID.randomUUID().toString()), name);
        dept.addDomainEvent(new AddDepartmentEvent(dept));
        return dept;
    }
}