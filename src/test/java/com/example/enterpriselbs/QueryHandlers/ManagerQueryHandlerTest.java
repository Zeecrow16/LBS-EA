package com.example.enterpriselbs.QueryHandlers;

import com.example.enterpriselbs.application.QueryHandlers.ManagerQueryHandler;
import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManagerQueryHandlerTest {

    private LeaveRequestRepository leaveRequestRepository;
    private StaffRepository staffRepository;
    private ManagerQueryHandler queryHandler;

    @BeforeEach
    void setUp() {
        leaveRequestRepository = mock(LeaveRequestRepository.class);
        staffRepository = mock(StaffRepository.class);
        queryHandler = new ManagerQueryHandler(staffRepository, leaveRequestRepository);
    }

    @Test
    @DisplayName("findOutstandingRequestsForStaff returns only pending requests for given staff")
    void testFindOutstandingRequestsForStaff() {
        LeaveRequestEntity pending = new LeaveRequestEntity();
        pending.setStaffId("STAFF001");
        pending.setStatus("PENDING");

        LeaveRequestEntity approved = new LeaveRequestEntity();
        approved.setStaffId("STAFF001");
        approved.setStatus("APPROVED");

        when(leaveRequestRepository.findByStaffId("STAFF001"))
                .thenReturn(List.of(pending, approved));

        List<LeaveRequestDto> result = queryHandler.findOutstandingRequestsForStaff("STAFF001");

        assertEquals(1, result.size());
        assertEquals("STAFF001", result.get(0).getStaffId());
    }

    @Test
    @DisplayName("remainingLeaveForStaff calculates correctly subtracting approved leave")
    void testRemainingLeaveForStaff() {
        StaffEntity staff = new StaffEntity();
        staff.setId("STAFF001");
        staff.setLeaveAllocation(10);

        LeaveRequestEntity approved1 = new LeaveRequestEntity();
        approved1.setStaffId("STAFF001");
        approved1.setStatus("APPROVED");
        approved1.setStartDate(LocalDate.of(2025, 9, 18));
        approved1.setEndDate(LocalDate.of(2025, 9, 19));

        LeaveRequestEntity approved2 = new LeaveRequestEntity();
        approved2.setStaffId("STAFF001");
        approved2.setStatus("APPROVED");
        approved2.setStartDate(LocalDate.of(2025, 9, 20));
        approved2.setEndDate(LocalDate.of(2025, 9, 20));

        when(staffRepository.findById("STAFF001")).thenReturn(Optional.of(staff));
        when(leaveRequestRepository.findByStaffId("STAFF001")).thenReturn(List.of(approved1, approved2));

        Optional<Integer> remaining = queryHandler.remainingLeaveForStaff("STAFF001");

        assertTrue(remaining.isPresent());
        assertEquals(7, remaining.get());
    }

    @Test
    @DisplayName("remainingLeaveForStaff returns empty if staff not found")
    void testRemainingLeaveForStaffStaffNotFound() {
        when(staffRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

        Optional<Integer> remaining = queryHandler.remainingLeaveForStaff("UNKNOWN");

        assertTrue(remaining.isEmpty());
    }

    @Test
    @DisplayName("findOutstandingRequestsForStaff returns empty list if none pending")
    void testFindOutstandingRequestsForStaffNonePending() {
        LeaveRequestEntity approved = new LeaveRequestEntity();
        approved.setStaffId("STAFF001");
        approved.setStatus("APPROVED");

        when(leaveRequestRepository.findByStaffId("STAFF001")).thenReturn(List.of(approved));

        List<LeaveRequestDto> result = queryHandler.findOutstandingRequestsForStaff("STAFF001");

        assertTrue(result.isEmpty());
    }
}
