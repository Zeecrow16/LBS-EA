package com.example.enterpriselbs.domain.valueObjects;

import com.example.enterpriselbs.domain.ValueObject;

import java.time.LocalDate;

public final class LeavePeriod extends ValueObject {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public LeavePeriod(LocalDate startDate, LocalDate endDate) {
        assertArgumentNotNull(startDate, "Start date cannot be null");
        assertArgumentNotNull(endDate, "End date cannot be null");

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }
    public int days() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public LocalDate startDate() { return startDate; }
    public LocalDate endDate() { return endDate; }
}