package com.example.enterpriselbs.controller;



import com.example.enterpriselbs.application.QueryHandlers.StaffQueryHandler;
import com.example.enterpriselbs.application.commands.CancelLeaveCommand;
import com.example.enterpriselbs.application.commands.RequestLeaveCommand;
import com.example.enterpriselbs.application.controllers.staff.StaffController;
import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.application.services.applicationService.StaffApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class StaffControllerTest {

    @Mock
    private StaffApplicationService staffService;

    @Mock
    private StaffQueryHandler staffQueryHandler;

    @InjectMocks
    private StaffController staffController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRequestLeave_Success() {

        RequestLeaveCommand cmd = new RequestLeaveCommand("S001", LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
        when(staffService.requestLeave(cmd.getStaffId(), cmd.getStartDate(), cmd.getEndDate()))
                .thenReturn("LR001");

        String result = staffController.requestLeave(cmd);


        assertEquals("LR001", result);
        verify(staffService, times(1)).requestLeave(cmd.getStaffId(), cmd.getStartDate(), cmd.getEndDate());
    }

    @Test
    void testCancelLeaveRequest_Success() {
        CancelLeaveCommand cmd = new CancelLeaveCommand("LR001");

        staffController.cancelLeaveRequest(cmd);


        verify(staffService, times(1)).cancelLeaveRequest("LR001");
    }

    @Test
    void testViewLeaveStatus_Success() {
        String staffId = "S001";
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId("LR001");
        when(staffQueryHandler.findLeaveRequests(staffId)).thenReturn(List.of(dto));

        List<LeaveRequestDto> result = staffController.viewLeaveStatus(staffId);


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("LR001", result.get(0).getId());
    }

    @Test
    void testViewLeaveStatus_NotFound() {
        String staffId = "S001";
        when(staffQueryHandler.findLeaveRequests(staffId)).thenReturn(List.of());

        assertThrows(ResponseStatusException.class, () -> staffController.viewLeaveStatus(staffId));
    }

    @Test
    void testGetRemainingLeave_Success() {
        String staffId = "S001";
        when(staffQueryHandler.findRemainingLeave(staffId)).thenReturn(10);

        int remaining = staffController.getRemainingLeave(staffId);

        assertEquals(10, remaining);
    }

    @Test
    void testGetRemainingLeave_NotFound() {
        String staffId = "S001";
        when(staffQueryHandler.findRemainingLeave(staffId)).thenThrow(new IllegalArgumentException());

        assertThrows(ResponseStatusException.class, () -> staffController.getRemainingLeave(staffId));
    }

}