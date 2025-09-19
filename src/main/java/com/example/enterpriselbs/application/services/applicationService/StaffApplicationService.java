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
        LOG.info("Staff {} requesting leave from {} to {}", staffId, startDate, endDate);

        StaffAggregate staff = staffRepository.findById(staffId)
                .map(StaffMapper::toDomain)
                .orElseThrow(() -> {
                    LOG.error("Staff id not found: {}", staffId);
                    return new IllegalArgumentException("Staff id not found");
                });

        LeaveRequestAggregate leaveRequest = LeaveRequestAggregate.leaveRequestOfWithEvent(
                UniqueIdFactory.createID(),
                staff.id(),
                new LeavePeriod(startDate, endDate)
        );

        leaveRequestRepository.save(LeaveRequestMapper.toJpa(leaveRequest));

        LOG.info("Leave request created with id={} for staff={}", leaveRequest.id().value(), staffId);
        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());

        return leaveRequest.id().value();
    }

    @Transactional
    public void cancelLeaveRequest(String leaveRequestId) {
        LOG.info("Staff attempting to cancel leave requestId={}", leaveRequestId);

        LeaveRequestAggregate leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .map(LeaveRequestMapper::toDomain)
                .orElseThrow(() -> {
                    LOG.error("Leave request not found for ID: {}", leaveRequestId);
                    return new IllegalArgumentException("Leave request id not found");
                });

        leaveRequest.cancel();
        leaveRequestRepository.save(LeaveRequestMapper.toJpa(leaveRequest));

        LOG.info("Leave request cancelled successfully: {}", leaveRequestId);
        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());
    }

}
