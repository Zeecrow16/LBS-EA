package com.example.enterpriselbs.infrastructure.repositories;

import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends CrudRepository<LeaveRequestEntity, String> {
    List<LeaveRequestEntity> findByStaffId(String staffId);
    List<LeaveRequestEntity> findByStatus(String status);
//    List<LeaveRequestEntity> findByStaffIdAndStatus(String staffId, String status);

}