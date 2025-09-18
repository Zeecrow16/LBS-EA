package com.example.enterpriselbs.domain.valueObjects;


import com.example.enterpriselbs.domain.ValueObject;

public class Password extends ValueObject {
    private final String value;

    public Password(String value) {
        assertArgumentNotEmpty(value, "Password cannot be empty");
        this.value = value;
    }

    public String value() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }
}
