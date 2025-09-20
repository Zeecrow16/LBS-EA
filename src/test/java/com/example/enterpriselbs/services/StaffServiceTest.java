package com.example.enterpriselbs.services;

import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.application.services.applicationService.StaffApplicationService;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.domain.aggregates.factory.UniqueIdFactory;
import com.example.enterpriselbs.domain.valueObjects.*;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.mappers.StaffMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class StaffServiceTest {

    private StaffRepository staffRepository;
    private LeaveRequestRepository leaveRequestRepository;
    private LocalDomainEventManager eventManager;
    private StaffApplicationService staffService;

    @BeforeEach
    void setUp() {
        staffRepository = mock(StaffRepository.class);
        leaveRequestRepository = mock(LeaveRequestRepository.class);
        eventManager = mock(LocalDomainEventManager.class);

        staffService = new StaffApplicationService(staffRepository, leaveRequestRepository, eventManager);
    }

    @Test
    @DisplayName("requestLeave succeeds when enough leave available")
    void testRequestLeave_Success() {
        String staffId = UniqueIdFactory.createID().value();
        StaffAggregate staff = StaffAggregate.createWithEvent(
                new Identity(staffId),
                "user",
                new FullName("John", "Tolkien"),
                Role.STAFF,
                null,
                null,
                10,
                new Password("pass")
        );
        when(staffRepository.findById(staffId)).thenReturn(Optional.of(StaffMapper.toJpa(staff)));
        when(leaveRequestRepository.findByStaffId(staffId)).thenReturn(List.of());

        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = start.plusDays(2);

        String leaveRequestId = staffService.requestLeave(staffId, start, end);

        assertNotNull(leaveRequestId);

        ArgumentCaptor<LeaveRequestEntity> captor = ArgumentCaptor.forClass(LeaveRequestEntity.class);
        verify(leaveRequestRepository).save(captor.capture());
        verify(eventManager).manageDomainEvents(eq(staffService), anyList());

        LeaveRequestEntity savedEntity = captor.getValue();
        assertEquals(LeaveStatus.PENDING, LeaveRequestMapper.toDomain(savedEntity).status());
    }

    @Test
    @DisplayName("requestLeave fails when requested days exceed remaining leave")
    void testRequestLeave_ExceedsLeave() {
        String staffId = UniqueIdFactory.createID().value();
        StaffAggregate staff = StaffAggregate.createWithEvent(
                new Identity(staffId),
                "user",
                new FullName("Douglas", "Adams"),
                Role.STAFF,
                null,
                null,
                3,
                new Password("dontpanic")
        );
        when(staffRepository.findById(staffId)).thenReturn(Optional.of(StaffMapper.toJpa(staff)));

        LeaveRequestAggregate approvedLeave = LeaveRequestAggregate.leaveRequestOf(
                UniqueIdFactory.createID(),
                staff.id(),
                new LeavePeriod(LocalDate.now().plusDays(1), LocalDate.now().plusDays(1)),
                LeaveStatus.APPROVED,
                "Approved"
        );
        when(leaveRequestRepository.findByStaffId(staffId))
                .thenReturn(List.of(LeaveRequestMapper.toJpa(approvedLeave)));

        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = start.plusDays(2);


        String result = staffService.requestLeave(staffId, start, end);

        assertEquals("Cannot create leave request: exceeds remaining annual leave", result);
        verify(leaveRequestRepository, never()).save(any());
        verify(eventManager, never()).manageDomainEvents(any(), anyList());
    }

    @Test
    @DisplayName("cancelLeaveRequest sets status to CANCELLED and saves")
    void testCancelLeaveRequest() {
        String leaveRequestId = UniqueIdFactory.createID().value();
        LeaveRequestAggregate leaveRequest = LeaveRequestAggregate.leaveRequestOf(
                UniqueIdFactory.createID(),
                UniqueIdFactory.createID(),
                new LeavePeriod(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)),
                LeaveStatus.PENDING,
                "Pending"
        );
        when(leaveRequestRepository.findById(leaveRequestId))
                .thenReturn(Optional.of(LeaveRequestMapper.toJpa(leaveRequest)));

        staffService.cancelLeaveRequest(leaveRequestId);

        ArgumentCaptor<LeaveRequestEntity> captor = ArgumentCaptor.forClass(LeaveRequestEntity.class);
        verify(leaveRequestRepository).save(captor.capture());
        LeaveRequestEntity savedEntity = captor.getValue();
        assertEquals(LeaveStatus.CANCELLED, LeaveRequestMapper.toDomain(savedEntity).status());
        verify(eventManager).manageDomainEvents(eq(staffService), anyList());
    }
}
