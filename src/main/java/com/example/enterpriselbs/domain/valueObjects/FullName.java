package com.example.enterpriselbs.domain.valueObjects;

import com.example.enterpriselbs.domain.ValueObject;
import lombok.ToString;

import java.util.Objects;

public class FullName extends ValueObject {

    private final String firstName;
    private final String surname;

    public FullName(String firstName, String surname) {
        assertArgumentNotEmpty(firstName, "First name cannot be empty");
        assertArgumentLength(firstName, 1, 20, "First name must be 1–20 characters");

        assertArgumentNotEmpty(surname, "Surname cannot be empty");
        assertArgumentLength(surname, 1, 20, "Surname must be 1–20 characters");

        this.firstName = firstName;
        this.surname = surname;
    }

    public String firstName() { return firstName; }
    public String surname() { return surname; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FullName)) return false;
        FullName other = (FullName) o;
        return firstName.equals(other.firstName) && surname.equals(other.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, surname);
    }

    @Override
    public String toString() {
        return firstName + " " + surname;
    }
}