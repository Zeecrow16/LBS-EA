package com.example.enterpriselbs.controller;

import com.example.enterpriselbs.application.controllers.staff.StaffController;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequest;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffControllerTest {

    private LeaveRequestRepository leaveRequestRepo;
    private StaffController staffController;

    @BeforeEach
    void setUp() {
        leaveRequestRepo = mock(LeaveRequestRepository.class);
        staffController = new StaffController(leaveRequestRepo);
    }

    @Test
    void testGetAllLeaveRequests() {
        LeaveRequest lr1 = new LeaveRequest();
        lr1.setId("lr-1");
        lr1.setStaffId("s-1");
        lr1.setStartDate(LocalDate.of(2025, 9, 20));
        lr1.setEndDate(LocalDate.of(2025, 9, 22));
        lr1.setStatus("PENDING");

        when(leaveRequestRepo.findByStaffId("s-1")).thenReturn(Arrays.asList(lr1));

        List<LeaveRequest> result = staffController.getAllLeaveRequests("s-1");
        assertEquals(1, result.size());
        assertEquals("lr-1", result.get(0).getId());
        assertEquals("PENDING", result.get(0).getStatus());
    }

    @Test
    void testRequestLeave() {
        LeaveRequest lr = new LeaveRequest();
        lr.setId("lr-2");
        lr.setStaffId("s-1");
        lr.setStartDate(LocalDate.of(2025, 9, 25));
        lr.setEndDate(LocalDate.of(2025, 9, 26));

        when(leaveRequestRepo.save(any(LeaveRequest.class))).thenAnswer(i -> i.getArguments()[0]);

        LeaveRequest saved = staffController.requestLeave(lr);
        assertEquals("PENDING", saved.getStatus());
        assertEquals("lr-2", saved.getId());
    }

    @Test
    void testCancelLeave_found() {
        LeaveRequest lr = new LeaveRequest();
        lr.setId("lr-3");
        lr.setStaffId("s-1");
        lr.setStatus("PENDING");

        when(leaveRequestRepo.findById("lr-3")).thenReturn(Optional.of(lr));
        when(leaveRequestRepo.save(any(LeaveRequest.class))).thenAnswer(i -> i.getArguments()[0]);

        LeaveRequest cancelled = staffController.cancelLeave("lr-3");
        assertEquals("CANCELLED", cancelled.getStatus());
    }

    @Test
    void testCancelLeave_notFound() {
        when(leaveRequestRepo.findById("lr-999")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            staffController.cancelLeave("lr-999");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}