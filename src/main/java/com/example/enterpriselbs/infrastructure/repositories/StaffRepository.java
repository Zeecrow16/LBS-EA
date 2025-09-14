package com.example.enterpriselbs.infrastructure.repositories;

import com.example.enterpriselbs.infrastructure.entities.Department;
import com.example.enterpriselbs.infrastructure.entities.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends CrudRepository<Staff, String> {
    List<Staff> findByManagerId(String managerId);
    List<Staff> findByDepartmentId(String departmentId);
}