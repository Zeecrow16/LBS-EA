package com.example.enterpriselbs.domain.aggregates;

import com.example.enterpriselbs.domain.BaseEntity;
import com.example.enterpriselbs.domain.Identity;

import java.util.Objects;

public class DepartmentAggregate extends BaseEntity {

    private String name;

    public DepartmentAggregate(Identity id, String name) {
        super(id);
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Department name is required");
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void changeName(String newName) {
        if (newName == null || newName.isBlank()) throw new IllegalArgumentException("Department name cannot be empty");
        this.name = newName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentAggregate)) return false;
        DepartmentAggregate other = (DepartmentAggregate) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}