package com.example.enterpriselbs.application.services.applicationService;

import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.mappers.StaffMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class ManagerApplicationService {
    private final StaffRepository staffRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LocalDomainEventManager localDomainEventManager;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

//    public List<LeaveRequestAggregate> viewOutstandingRequests(String staffId) {
//        return leaveRequestRepository.findByStaffId(staffId).stream()
//                .filter(l -> l.getStatus().equalsIgnoreCase("PENDING"))
//                .map(LeaveRequestMapper::toDomain)
//                .toList();
//    }
//    @Transactional
//    public void approveLeave(String leaveRequestId) {
//        LeaveRequestAggregate leaveRequest = findLeaveRequestAggregate(leaveRequestId);
//        leaveRequest.approve();
//        leaveRequestRepository.save(LeaveRequestMapper.toJpa(leaveRequest));
//        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());
//    }
//
//    @Transactional
//    public void rejectLeave(String leaveRequestId) {
//        LeaveRequestAggregate leaveRequest = findLeaveRequestAggregate(leaveRequestId);
//        leaveRequest.reject();
//        leaveRequestRepository.save(LeaveRequestMapper.toJpa(leaveRequest));
//        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());
//    }
//
//    public int remainingLeaveForStaff(String staffId) {
//        StaffAggregate staff = findStaffAggregate(staffId);
//        int approvedDays = leaveRequestRepository.findByStaffId(staffId).stream()
//                .filter(l -> l.getStatus().equalsIgnoreCase("APPROVED"))
//                .mapToInt(l -> l.getEndDate().getDayOfYear() - l.getStartDate().getDayOfYear() + 1)
//                .sum();
//        return staff.leaveAllocation() - approvedDays;
//    }
//
//    private StaffAggregate findStaffAggregate(String staffId) {
//        Optional<StaffAggregate> staff = staffRepository.findById(staffId)
//                .map(StaffMapper::toDomain);
//        return staff.orElseThrow(() -> new IllegalArgumentException("Staff id not found"));
//    }
//
//    private LeaveRequestAggregate findLeaveRequestAggregate(String leaveRequestId) {
//        Optional<LeaveRequestAggregate> leaveRequest = leaveRequestRepository.findById(leaveRequestId)
//                .map(l -> LeaveRequestMapper.toDomain((com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity) l));
//        return leaveRequest.orElseThrow(() -> new IllegalArgumentException("Leave request id not found"));
//    }
}
