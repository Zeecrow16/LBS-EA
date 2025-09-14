package com.example.enterpriselbs.infrastructure.repositories;

import com.example.enterpriselbs.infrastructure.entities.LeaveRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends CrudRepository<LeaveRequest, String> {
    List<LeaveRequest> findByStaffId(String staffId);

    List<LeaveRequest> findByStatus(String status);
}