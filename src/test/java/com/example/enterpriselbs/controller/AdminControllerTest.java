package com.example.enterpriselbs.controller;

import com.example.enterpriselbs.application.QueryHandlers.AdminQueryHandler;
import com.example.enterpriselbs.application.commands.*;
import com.example.enterpriselbs.application.controllers.admin.AdminController;
import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.application.services.applicationService.AdminApplicationService;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminApplicationService adminService;

    @Mock
    private AdminQueryHandler queryHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewOutstandingLeaveRequests_Success() {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId("LR001");
        dto.setStaffId("STAFF001");
        dto.setStatus(LeaveStatus.PENDING);

        when(queryHandler.findOutstandingLeaveRequests(null, null))
                .thenReturn(List.of(dto));

        List<LeaveRequestDto> result = adminController.viewOutstandingLeaveRequests(null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("LR001", result.get(0).getId());
    }

    @Test
    void testViewOutstandingLeaveRequests_NotFound() {
        when(queryHandler.findOutstandingLeaveRequests(null, null))
                .thenReturn(new ArrayList<>());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                adminController.viewOutstandingLeaveRequests(null, null)
        );

        assertEquals("404 NOT_FOUND \"No outstanding leave requests found\"", exception.getMessage());
    }
    @Test
    void testAddStaff_Success() {
        AddStaffCommand cmd = new AddStaffCommand(
                "i.robot",
                "Isaac",
                "Asimov",
                null,
                null,
                null,
                20,
                "password"
        );
        when(adminService.addStaff(cmd)).thenReturn("STAFF001");

        String staffId = adminController.addStaff(cmd);

        assertEquals("STAFF001", staffId);
        verify(adminService, times(1)).addStaff(cmd);
    }

    @Test
    void testAmendRole_Success() {
        AmendRoleCommand cmd = new AmendRoleCommand("STAFF001", null);

        adminController.amendRole(cmd);

        verify(adminService, times(1)).amendRole(cmd);
    }

    @Test
    void testAmendDepartment_Success() {
        AmendDepartmentCommand cmd = new AmendDepartmentCommand("STAFF001", "DEPT001");

        adminController.amendDepartment(cmd);

        verify(adminService, times(1)).amendDepartment(cmd);
    }

    @Test
    void testAmendLeaveAllocation_Success() {
        AmendAnnualLeaveCommand cmd = new AmendAnnualLeaveCommand("STAFF001", 25);

        adminController.amendLeaveAllocation(cmd);

        verify(adminService, times(1)).amendLeaveAllocation(cmd);
    }

    @Test
    void testApproveLeave_Success() {
        AdminApproveLeaveCommand cmd = new AdminApproveLeaveCommand("LR001");

        adminController.approveLeave(cmd);

        verify(adminService, times(1)).approveLeave(cmd);
    }
}