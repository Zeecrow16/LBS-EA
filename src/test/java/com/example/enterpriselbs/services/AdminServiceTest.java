package com.example.enterpriselbs.services;

import com.example.enterpriselbs.application.commands.AddStaffCommand;
import com.example.enterpriselbs.application.commands.AmendAnnualLeaveCommand;
import com.example.enterpriselbs.application.commands.AmendRoleCommand;
import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.application.services.applicationService.AdminApplicationService;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.domain.aggregates.factory.UniqueIdFactory;
import com.example.enterpriselbs.domain.valueObjects.FullName;
import com.example.enterpriselbs.domain.valueObjects.Password;
import com.example.enterpriselbs.domain.valueObjects.Role;
import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import com.example.enterpriselbs.infrastructure.mappers.StaffMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    private StaffRepository staffRepository;
    private LeaveRequestRepository leaveRequestRepository;
    private LocalDomainEventManager eventManager;
    private AdminApplicationService adminService;
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        staffRepository = mock(StaffRepository.class);
        leaveRequestRepository = mock(LeaveRequestRepository.class);
        eventManager = mock(LocalDomainEventManager.class);
        passwordEncoder = mock(org.springframework.security.crypto.password.PasswordEncoder.class);
        adminService = new AdminApplicationService(staffRepository, leaveRequestRepository, eventManager, passwordEncoder);
    }

    @Test
    @DisplayName("addStaff hashes password and saves staff")
    void testAddStaff() {
        AddStaffCommand cmd = new AddStaffCommand(
                "jdoe", "John", "Doe",
                Role.STAFF,
                null, null,
                20,
                "Secret123!"
        );
        when(passwordEncoder.encode(cmd.getPassword())).thenReturn("hashedSecret");

        ArgumentCaptor<StaffEntity> captor = ArgumentCaptor.forClass(StaffEntity.class);

        String staffId = adminService.addStaff(cmd);

        verify(staffRepository).save(captor.capture());
        StaffEntity saved = captor.getValue();
        assertEquals("jdoe", saved.getUsername());
        assertEquals("hashedSecret", saved.getPasswordHash());
        assertEquals(20, saved.getLeaveAllocation());
        assertNotNull(staffId);
    }

    @Test
    @DisplayName("amendRole updates role and triggers events")
    void testAmendRole() {
        String staffId = UniqueIdFactory.createID().value();
        StaffAggregate staff = StaffAggregate.createWithEvent(
                new Identity(staffId), "user", new FullName("Douglas", "Adams"), Role.STAFF,
                null, null, 10, new Password("pass")
        );
        when(staffRepository.findById(staffId)).thenReturn(java.util.Optional.of(StaffMapper.toJpa(staff)));

        AmendRoleCommand cmd = new AmendRoleCommand(staffId, Role.MANAGER);
        ArgumentCaptor<StaffEntity> captor = ArgumentCaptor.forClass(StaffEntity.class);

        adminService.amendRole(cmd);

        verify(staffRepository).save(captor.capture());
        StaffAggregate updatedStaff = StaffMapper.toDomain(captor.getValue());
        assertEquals(Role.MANAGER, updatedStaff.role());
    }

    @Test
    @DisplayName("amendLeaveAllocation updates leave allocation")
    void testAmendLeaveAllocation() {
        String staffId = UniqueIdFactory.createID().value();
        StaffAggregate staff = StaffAggregate.createWithEvent(
                new Identity(staffId), "user", new FullName("A", "B"), Role.STAFF,
                null, null, 10, new Password("pass")
        );
        when(staffRepository.findById(staffId)).thenReturn(java.util.Optional.of(StaffMapper.toJpa(staff)));

        AmendAnnualLeaveCommand cmd = new AmendAnnualLeaveCommand(staffId, 15);
        ArgumentCaptor<StaffEntity> captor = ArgumentCaptor.forClass(StaffEntity.class);

        adminService.amendLeaveAllocation(cmd);

        verify(staffRepository).save(captor.capture());
        StaffAggregate updatedStaff = StaffMapper.toDomain(captor.getValue());
        assertEquals(15, updatedStaff.leaveAllocation());
    }

}
