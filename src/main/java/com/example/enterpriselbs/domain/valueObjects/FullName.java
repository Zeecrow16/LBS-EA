package com.example.enterpriselbs.domain.valueObjects;

import com.example.enterpriselbs.domain.ValueObject;
import lombok.ToString;

import java.util.Objects;

public final class FullName extends ValueObject {
    private final String firstName;
    private final String surname;

    public FullName(String firstName, String surname){
        assertArgumentNotEmpty(firstName, "First name cannot be empty");
        assertArgumentNotEmpty(surname, "Surname cannot be empty");
        this.firstName = firstName;
        this.surname = surname;
    }

    public String firstName() { return firstName; }
    public String surname() { return surname; }


}
