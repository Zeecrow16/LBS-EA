package com.example.enterpriselbs.domain;

import lombok.AllArgsConstructor;

public abstract class BaseEntity {

    protected final Identity id;

    protected BaseEntity() {
        this.id = null;
    }

    protected BaseEntity(Identity id) {
        if (id == null) throw new IllegalArgumentException("ID cannot be null");
        this.id = id;
    }

    public Identity id() { return id; }
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        BaseEntity other = (BaseEntity) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}