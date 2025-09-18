package com.example.enterpriselbs.domain;

import lombok.AllArgsConstructor;

import java.util.Objects;

public abstract class BaseEntity {
    protected final Identity id;

    protected BaseEntity(Identity id) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
    }

    public Identity id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

//public abstract class BaseEntity {
//
//    protected final Identity id;
//
//    protected BaseEntity() {
//        this.id = null;
//    }
//
//    protected BaseEntity(Identity id) {
//        if (id == null) throw new IllegalArgumentException("ID cannot be null");
//        this.id = id;
//    }
//
//    public Identity id() { return id; }
//    @Override
//    public boolean equals(Object o) {
//        if (o == null || o.getClass() != this.getClass()) return false;
//        BaseEntity other = (BaseEntity) o;
//        return id.equals(other.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }
//}