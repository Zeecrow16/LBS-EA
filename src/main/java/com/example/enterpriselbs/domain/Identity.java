package com.example.enterpriselbs.domain;

import java.util.Objects;



public class Identity {

    private final String value;

    public Identity(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Identity cannot be null or blank");
        }
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identity)) return false;
        Identity other = (Identity) o;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
