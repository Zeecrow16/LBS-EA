package com.example.enterpriselbs.infrastructure.repositories;

import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StaffRepository extends CrudRepository<StaffEntity, String> {
//    List<StaffEntity> findByManagerId(String managerId);
//    List<StaffEntity> findByRole(String role);
//    List<StaffEntity> findByDepartmentId(String departmentId);

}