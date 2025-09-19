package com.example.enterpriselbs.application.QueryHandlers;

import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ManagerQueryHandler {

    private final StaffRepository staffRepository;
    private final LeaveRequestRepository leaveRequestRepository;

//    public Iterable<?> findAllLeaveRequestsForTeam() {
//        return leaveRequestRepository.findAll();
//    }
//
//    public Optional<Iterable<LeaveRequestDto>> findOutstandingLeaveRequestsForTeam(String managerId) {
//        // Filter by managerId if repository supports, for now fetch all PENDING
//        List<LeaveRequestDto> list = leaveRequestRepository.findByStatus("PENDING").stream()
//                .map(LeaveRequestMapper::toDTO)
//                .collect(Collectors.toList());
//
//        return Optional.of(list);
//    }
//
//    public Optional<Iterable<LeaveRequestDto>> findLeaveRequestsForStaff(String staffId) {
//        List<LeaveRequestDto> list = leaveRequestRepository.findByStaffId(staffId).stream()
//                .map(LeaveRequestMapper::toDTO)
//                .collect(Collectors.toList());
//
//        return Optional.of(list);
//    }
//
//    public Optional<Integer> remainingLeaveForStaff(String staffId) {
//        return staffRepository.findById(staffId)
//                .map(staff -> {
//                    int approvedDays = leaveRequestRepository.findByStaffId(staffId).stream()
//                            .filter(l -> l.getStatus().equalsIgnoreCase("APPROVED"))
//                            .mapToInt(l -> l.getEndDate().getDayOfYear() - l.getStartDate().getDayOfYear() + 1)
//                            .sum();
//                    return staff.getLeaveAllocation() - approvedDays;
//                });
//    }
}
