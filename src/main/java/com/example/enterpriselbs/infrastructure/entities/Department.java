package com.example.enterpriselbs.infrastructure.entities;

import com.example.enterpriselbs.infrastructure.constants.DepartmentConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @Column(name = "department_id")
    private String id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    public Department() {}

    public Department(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}