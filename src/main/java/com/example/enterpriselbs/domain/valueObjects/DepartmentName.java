package com.example.enterpriselbs.domain.valueObjects;

import com.example.enterpriselbs.domain.ValueObject;

public class DepartmentName extends ValueObject {
    private final String value;

    public DepartmentName(String value) {
        assertArgumentNotEmpty(value, "Department name cannot be empty");
        this.value = value;
    }

    public String value() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentName other)) return false;
        return value.equals(other.value);
    }
    @Override
    public int hashCode() { return value.hashCode(); }

    @Override
    public String toString() {
        return value;
    }

}
