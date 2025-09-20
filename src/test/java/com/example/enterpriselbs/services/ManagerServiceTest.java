package com.example.enterpriselbs.services;

import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.application.services.applicationService.ManagerApplicationService;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.aggregates.factory.UniqueIdFactory;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ManagerServiceTest {
    private LeaveRequestRepository leaveRequestRepository;
    private LocalDomainEventManager localDomainEventManager;
    private ManagerApplicationService managerService;

    @BeforeEach
    void setup() {
        leaveRequestRepository = mock(LeaveRequestRepository.class);
        localDomainEventManager = mock(LocalDomainEventManager.class);
        managerService = new ManagerApplicationService(leaveRequestRepository, localDomainEventManager);
    }


    @Test
    @DisplayName("approveLeave sets leave status to APPROVED and saves")
    void testApproveLeave() {
        String leaveRequestId = "LR001";
        LeaveRequestAggregate leaveRequest = LeaveRequestAggregate.leaveRequestOf(
                new Identity(leaveRequestId),
                new Identity("STAFF002"),
                new LeavePeriod(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2)),
                LeaveStatus.PENDING,
                "Pending approval"
        );

        LeaveRequestEntity entity = LeaveRequestMapper.toJpa(leaveRequest);
        when(leaveRequestRepository.findById(leaveRequestId)).thenReturn(Optional.of(entity));

        ArgumentCaptor<LeaveRequestEntity> captor = ArgumentCaptor.forClass(LeaveRequestEntity.class);

        managerService.approveLeave(leaveRequestId);

        verify(leaveRequestRepository).save(captor.capture());
        LeaveRequestAggregate savedRequest = LeaveRequestMapper.toDomain(captor.getValue());
        assertEquals(LeaveStatus.APPROVED, savedRequest.status());
    }

    @Test
    @DisplayName("rejectLeave sets leave status to REJECTED and saves")
    void testRejectLeave() {
        // Arrange
        String leaveRequestId = "LR002";
        LeaveRequestAggregate leaveRequest = LeaveRequestAggregate.leaveRequestOf(
                new Identity(leaveRequestId),
                new Identity("STAFF002"),
                new LeavePeriod(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2)),
                LeaveStatus.PENDING,
                "Pending approval"
        );

        LeaveRequestEntity entity = LeaveRequestMapper.toJpa(leaveRequest);
        when(leaveRequestRepository.findById(leaveRequestId)).thenReturn(Optional.of(entity));

        ArgumentCaptor<LeaveRequestEntity> captor = ArgumentCaptor.forClass(LeaveRequestEntity.class);

        managerService.rejectLeave(leaveRequestId);

        verify(leaveRequestRepository).save(captor.capture());
        LeaveRequestAggregate savedRequest = LeaveRequestMapper.toDomain(captor.getValue());
        assertEquals(LeaveStatus.REJECTED, savedRequest.status());
    }

}
