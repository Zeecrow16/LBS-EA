package com.example.enterpriselbs.domain;

import java.util.Objects;

public final class Identity extends ValueObject {
    private final String value;

    public Identity(String value) {
        assertArgumentNotEmpty(value, "Identity cannot be empty");
        this.value = value;
    }

    public String value() {
        return value;
    }

}

