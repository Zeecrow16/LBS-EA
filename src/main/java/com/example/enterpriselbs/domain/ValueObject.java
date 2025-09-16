package com.example.enterpriselbs.domain;

public abstract class ValueObject {

    protected void assertArgumentNotEmpty(String value, String message) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(message);
    }

    protected void assertArgumentLength(String value, int min, int max, String message) {
        if (value == null || value.length() < min || value.length() > max) throw new IllegalArgumentException(message);
    }
}