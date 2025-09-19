package com.example.enterpriselbs.application.services.applicationService;

import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.domain.aggregates.factory.UniqueIdFactory;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.mappers.StaffMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StaffApplicationService {
    private final StaffRepository staffRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LocalDomainEventManager localDomainEventManager;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Transactional
    public String requestLeave(String staffId, LocalDate startDate, LocalDate endDate) {
        StaffAggregate staff = staffRepository.findById(staffId)
                .map(StaffMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Staff id not found"));

        LeaveRequestAggregate leaveRequest = new LeaveRequestAggregate(
                UniqueIdFactory.createID(),
                staff.id(),
                new LeavePeriod(startDate, endDate),
                LeaveStatus.PENDING
        );

        leaveRequestRepository.save(LeaveRequestMapper.toJpa(leaveRequest));
        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());

        return leaveRequest.id().value();
    }

    @Transactional
    public void cancelLeaveRequest(String leaveRequestId) {
        LeaveRequestAggregate leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .map(LeaveRequestMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Leave request id not found"));

        leaveRequest.cancel();
        leaveRequestRepository.save(LeaveRequestMapper.toJpa(leaveRequest));
        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());
    }

    private StaffAggregate findStaffAggregate(String staffId) {
        Optional<StaffAggregate> staff = staffRepository.findById(staffId)
                .map(StaffMapper::toDomain);
        return staff.orElseThrow(() -> new IllegalArgumentException("Staff id not found"));
    }

}
