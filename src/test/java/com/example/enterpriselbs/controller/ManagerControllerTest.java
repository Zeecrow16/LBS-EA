package com.example.enterpriselbs.controller;

import com.example.enterpriselbs.application.QueryHandlers.ManagerQueryHandler;
import com.example.enterpriselbs.application.commands.ApproveLeaveCommand;
import com.example.enterpriselbs.application.commands.RejectLeaveCommand;
import com.example.enterpriselbs.application.controllers.manager.ManagerController;
import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.application.services.applicationService.ManagerApplicationService;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ManagerControllerTest {

    @Mock
    private ManagerApplicationService managerService;

    @Mock
    private ManagerQueryHandler managerQueryHandler;

    @InjectMocks
    private ManagerController managerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewOutstandingRequestsForStaff_Success() {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId("LR001");
        dto.setStaffId("STAFF001");
        dto.setStartDate(LocalDate.now().plusDays(1));
        dto.setEndDate(LocalDate.now().plusDays(3));
        dto.setStatus(LeaveStatus.PENDING);

        when(managerQueryHandler.findOutstandingRequestsForStaff("STAFF001"))
                .thenReturn(List.of(dto));

        List<LeaveRequestDto> result = managerController.viewOutstandingRequestsForStaff("STAFF001");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("LR001", result.get(0).getId());
        assertEquals("STAFF001", result.get(0).getStaffId());
    }

    @Test
    void testViewOutstandingRequestsForStaff_NotFound() {
        when(managerQueryHandler.findOutstandingRequestsForStaff("STAFF001"))
                .thenReturn(new ArrayList<>());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                managerController.viewOutstandingRequestsForStaff("STAFF001")
        );
        assertEquals("404 NOT_FOUND \"No outstanding requests found\"", exception.getMessage());
    }

    @Test
    void testViewRemainingLeaveForStaff_Success() {
        when(managerQueryHandler.remainingLeaveForStaff("STAFF001"))
                .thenReturn(Optional.of(15));

        int remainingLeave = managerController.viewRemainingLeaveForStaff("STAFF001");

        assertEquals(15, remainingLeave);
    }

    @Test
    void testViewRemainingLeaveForStaff_NotFound() {
        when(managerQueryHandler.remainingLeaveForStaff("STAFF001"))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                managerController.viewRemainingLeaveForStaff("STAFF001")
        );
        assertEquals("404 NOT_FOUND \"Staff id not found\"", exception.getMessage());
    }

    @Test
    void testApproveLeave_Success() {
        ApproveLeaveCommand cmd = new ApproveLeaveCommand("LR001");

        managerController.approveLeave(cmd);

        verify(managerService, times(1)).approveLeave("LR001");
    }


    @Test
    void testRejectLeave_Success() {

        RejectLeaveCommand cmd = new RejectLeaveCommand("LR001");

        managerController.rejectLeave(cmd);


        verify(managerService, times(1)).rejectLeave("LR001");
    }


}