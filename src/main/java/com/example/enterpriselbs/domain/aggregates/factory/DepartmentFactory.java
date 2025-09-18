package com.example.enterpriselbs.domain.aggregates.factory;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.DepartmentAggregate;
import com.example.enterpriselbs.domain.events.AddDepartmentEvent;
import com.example.enterpriselbs.domain.valueObjects.DepartmentName;
import org.springframework.stereotype.Component;

@Component
public class DepartmentFactory {
    public DepartmentAggregate create(DepartmentName name) {
        return DepartmentAggregate.createWithEvent(name);
    }
}