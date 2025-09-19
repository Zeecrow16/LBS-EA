package com.example.enterpriselbs.application.QueryHandlers;

import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.application.dto.StaffDto;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.mappers.StaffMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class AdminQueryHandler {
    private final StaffRepository staffRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    public List<LeaveRequestDto> findOutstandingLeaveRequests(String staffId, String managerId) {
        return leaveRequestRepository.findByStatus("PENDING").stream()
                .filter(entity -> {
                    if (staffId != null) {
                        return entity.getStaffId().equals(staffId);
                    }
                    if (managerId != null) {
                        // staffRepository must expose staff with managerId
                        return staffRepository.findById(entity.getStaffId())
                                .map(staff -> managerId.equals(staff.getManagerId()))
                                .orElse(false);
                    }
                    return true; // company-wide
                })
                .map(LeaveRequestMapper::toDTO)
                .collect(Collectors.toList());
    }



}
