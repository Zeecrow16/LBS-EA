package com.example.enterpriselbs.domain.valueObjects;

import com.example.enterpriselbs.domain.ValueObject;

import java.time.LocalDate;

public class LeavePeriod extends ValueObject {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public LeavePeriod(LocalDate startDate, LocalDate endDate) {
        //insert value object code - date allowing thing
        if (startDate == null) throw new IllegalArgumentException("Start date cannot be null");
        if (endDate == null) throw new IllegalArgumentException("End date cannot be null");
        if (endDate.isBefore(startDate)) throw new IllegalArgumentException("End date cannot be before start date");
        if (startDate.isBefore(LocalDate.now())) throw new IllegalArgumentException("Leave cannot start in the past");

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate startDate() { return startDate; }
    public LocalDate endDate() { return endDate; }

    public int days() {
        return (int) (endDate.toEpochDay() - startDate.toEpochDay() + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LeavePeriod)) return false;
        LeavePeriod other = (LeavePeriod) o;
        return startDate.equals(other.startDate) && endDate.equals(other.endDate);
    }

    @Override
    public int hashCode() {
        return startDate.hashCode() * 31 + endDate.hashCode();
    }

    @Override
    public String toString() {
        return startDate + " to " + endDate;
    }
}
