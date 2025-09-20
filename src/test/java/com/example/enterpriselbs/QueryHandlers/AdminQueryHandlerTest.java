package com.example.enterpriselbs.QueryHandlers;

import com.example.enterpriselbs.application.QueryHandlers.AdminQueryHandler;
import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminQueryHandlerTest {
    private LeaveRequestRepository leaveRequestRepository;
    private StaffRepository staffRepository;
    private AdminQueryHandler queryHandler;

    @BeforeEach
    void setUp() {
        leaveRequestRepository = mock(LeaveRequestRepository.class);
        staffRepository = mock(StaffRepository.class);
        queryHandler = new AdminQueryHandler(staffRepository, leaveRequestRepository);
    }

    @Test
    @DisplayName("findOutstandingLeaveRequests returns all pending requests when no filter is applied")
    void testFindAllPending() {
        LeaveRequestEntity pending1 = new LeaveRequestEntity();
        pending1.setStaffId("STAFF001");
        pending1.setStatus("PENDING");

        LeaveRequestEntity pending2 = new LeaveRequestEntity();
        pending2.setStaffId("STAFF002");
        pending2.setStatus("PENDING");

        when(leaveRequestRepository.findByStatus("PENDING")).thenReturn(List.of(pending1, pending2));

        List<LeaveRequestDto> result = queryHandler.findOutstandingLeaveRequests(null, null);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("findOutstandingLeaveRequests filters by staffId")
    void testFilterByStaffId() {
        LeaveRequestEntity pending = new LeaveRequestEntity();
        pending.setStaffId("STAFF001");
        pending.setStatus("PENDING");

        when(leaveRequestRepository.findByStatus("PENDING")).thenReturn(List.of(pending));

        List<LeaveRequestDto> result = queryHandler.findOutstandingLeaveRequests("STAFF001", null);

        assertEquals(1, result.size());
        assertEquals("STAFF001", result.get(0).getStaffId());
    }

    @Test
    @DisplayName("findOutstandingLeaveRequests filters by managerId")
    void testFilterByManagerId() {
        LeaveRequestEntity pending = new LeaveRequestEntity();
        pending.setStaffId("STAFF001");
        pending.setStatus("PENDING");

        StaffEntity staff = new StaffEntity();
        staff.setId("STAFF001");
        staff.setManagerId("MGR001");

        when(leaveRequestRepository.findByStatus("PENDING")).thenReturn(List.of(pending));
        when(staffRepository.findById("STAFF001")).thenReturn(Optional.of(staff));

        List<LeaveRequestDto> result = queryHandler.findOutstandingLeaveRequests(null, "MGR001");

        assertEquals(1, result.size());
        assertEquals("STAFF001", result.get(0).getStaffId());
    }

    @Test
    @DisplayName("findOutstandingLeaveRequests returns empty list when no matches")
    void testNoMatches() {
        when(leaveRequestRepository.findByStatus("PENDING")).thenReturn(List.of());

        List<LeaveRequestDto> result = queryHandler.findOutstandingLeaveRequests("STAFF999", null);

        assertTrue(result.isEmpty());
    }
}
