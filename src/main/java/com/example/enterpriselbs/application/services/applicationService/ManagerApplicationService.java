package com.example.enterpriselbs.application.services.applicationService;

import com.example.enterpriselbs.application.dto.LeaveRequestDto;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ManagerApplicationService {
    private final StaffRepository staffRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LocalDomainEventManager localDomainEventManager;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Transactional
    public void approveLeave(String leaveRequestId) {
        LOG.info("Manager attempting to approve leave requestId={}", leaveRequestId);

        LeaveRequestAggregate leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .map(LeaveRequestMapper::toDomain)
                .orElseThrow(() -> {
                    LOG.error("Leave request not found for ID: {}", leaveRequestId);
                    return new IllegalArgumentException("Leave request not found");
                });

        leaveRequest.approve();
        leaveRequestRepository.save(LeaveRequestMapper.toJpa(leaveRequest));

        LOG.info("Leave request approved for requestId={}", leaveRequest.id().value());
        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());
    }

    @Transactional
    public void rejectLeave(String leaveRequestId) {
        LOG.info("Manager attempting to reject leave requestId={}", leaveRequestId);

        LeaveRequestAggregate leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .map(LeaveRequestMapper::toDomain)
                .orElseThrow(() -> {
                    LOG.error("Leave request not found for ID: {}", leaveRequestId);
                    return new IllegalArgumentException("Leave request not found");
                });

        leaveRequest.reject();
        leaveRequestRepository.save(LeaveRequestMapper.toJpa(leaveRequest));

        LOG.info("Leave request rejected for requestId={}", leaveRequest.id().value());
        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());
    }
}
