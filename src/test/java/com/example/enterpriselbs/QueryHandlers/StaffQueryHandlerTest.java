package com.example.enterpriselbs.QueryHandlers;

import com.example.enterpriselbs.application.QueryHandlers.StaffQueryHandler;
import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StaffQueryHandlerTest {
    private StaffRepository staffRepository;
    private LeaveRequestRepository leaveRequestRepository;
    private StaffQueryHandler queryHandler;

    @BeforeEach
    void setUp() {
        staffRepository = mock(StaffRepository.class);
        leaveRequestRepository = mock(LeaveRequestRepository.class);
        queryHandler = new StaffQueryHandler(staffRepository, leaveRequestRepository);
    }

    @Test
    @DisplayName("findLeaveRequests returns all leave requests for a staff")
    void testFindLeaveRequests() {
        LeaveRequestEntity leave1 = new LeaveRequestEntity();
        leave1.setStaffId("STAFF001");
        leave1.setStatus("APPROVED");

        LeaveRequestEntity leave2 = new LeaveRequestEntity();
        leave2.setStaffId("STAFF001");
        leave2.setStatus("PENDING");

        when(leaveRequestRepository.findByStaffId("STAFF001"))
                .thenReturn(List.of(leave1, leave2));

        List<LeaveRequestDto> result = queryHandler.findLeaveRequests("STAFF001");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getStatus() == LeaveStatus.APPROVED));
        assertTrue(result.stream().anyMatch(dto -> dto.getStatus() == LeaveStatus.PENDING));
    }

    @Test
    @DisplayName("findRemainingLeave calculates correctly subtracting approved leave")
    void testFindRemainingLeave() {
        StaffEntity staff = new StaffEntity();
        staff.setId("STAFF001");
        staff.setFirstName("John");
        staff.setSurname("Tolkien");
        staff.setLeaveAllocation(10);
        staff.setRole("STAFF");
        staff.setPasswordHash("youshallnotpass!");

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
        when(leaveRequestRepository.findByStaffId("STAFF001"))
                .thenReturn(List.of(approved1, approved2));

        int remaining = queryHandler.findRemainingLeave("STAFF001");

        assertEquals(7, remaining);
    }

    @Test
    @DisplayName("findRemainingLeave throws if staff not found")
    void testFindRemainingLeaveStaffNotFound() {
        when(staffRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> queryHandler.findRemainingLeave("UNKNOWN"));
        assertEquals("Staff id not found", ex.getMessage());
    }

    @Test
    @DisplayName("findLeaveRequests returns empty list if no leave requests")
    void testFindLeaveRequestsEmpty() {
        when(leaveRequestRepository.findByStaffId("STAFF001")).thenReturn(List.of());

        List<LeaveRequestDto> result = queryHandler.findLeaveRequests("STAFF001");

        assertTrue(result.isEmpty());
    }

}
