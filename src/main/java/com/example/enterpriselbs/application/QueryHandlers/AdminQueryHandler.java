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

//    public List<LeaveRequestDto> findLeaveRequests(String staffId) {
//        return leaveRequestRepository.findByStaffId(staffId).stream()
//                .map(LeaveRequestMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    public int findRemainingLeave(String staffId) {
//        return staffRepository.findById(staffId)
//                .map(StaffMapper::toDomain)
//                .map(staff -> {
//                    int approvedDays = leaveRequestRepository.findByStaffId(staffId).stream()
//                            .filter(l -> l.getStatus().equalsIgnoreCase("APPROVED"))
//                            .mapToInt(l -> l.getEndDate().getDayOfYear() - l.getStartDate().getDayOfYear() + 1)
//                            .sum();
//                    return staff.leaveAllocation() - approvedDays;
//                })
//                .orElseThrow(() -> new IllegalArgumentException("Staff id not found"));
//    }

}
