package com.example.enterpriselbs.controller;

import com.example.enterpriselbs.application.controllers.manager.ManagerController;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequest;
import com.example.enterpriselbs.infrastructure.entities.Staff;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerControllerTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepo;

    @Mock
    private StaffRepository staffRepo;

    @InjectMocks
    private ManagerController managerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOutstandingRequests_returnsPendingRequestsForManager() {
        Staff staff = new Staff();
        staff.setId("s-1");

        LeaveRequest pendingRequest = new LeaveRequest();
        pendingRequest.setId("lr-1");
        pendingRequest.setStaffId("s-1");
        pendingRequest.setStatus("PENDING");

        when(staffRepo.findByManagerId("m-1")).thenReturn(List.of(staff));
        when(leaveRequestRepo.findByStatus("PENDING")).thenReturn(List.of(pendingRequest));

        List<LeaveRequest> result = managerController.getOutstandingRequests("m-1");

        assertEquals(1, result.size());
        assertEquals("lr-1", result.get(0).getId());
        verify(staffRepo).findByManagerId("m-1");
        verify(leaveRequestRepo).findByStatus("PENDING");
    }

    @Test
    void testApproveLeave_found() {
        LeaveRequest lr = new LeaveRequest();
        lr.setId("lr-2");
        lr.setStaffId("s-1");
        lr.setStatus("PENDING");

        when(leaveRequestRepo.findById("lr-2")).thenReturn(Optional.of(lr));
        when(leaveRequestRepo.save(any(LeaveRequest.class))).thenAnswer(i -> i.getArguments()[0]);

        ResponseEntity<Void> response = managerController.approveLeave("lr-2");

        assertEquals("APPROVED", lr.getStatus());
        assertEquals(204, response.getStatusCodeValue());
        verify(leaveRequestRepo).save(lr);
    }

    @Test
    void testApproveLeave_notFound() {
        when(leaveRequestRepo.findById("lr-99")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> managerController.approveLeave("lr-99"));
    }

    @Test
    void testRejectLeave_found() {
        LeaveRequest lr = new LeaveRequest();
        lr.setId("lr-3");
        lr.setStaffId("s-1");
        lr.setStatus("PENDING");

        when(leaveRequestRepo.findById("lr-3")).thenReturn(Optional.of(lr));
        when(leaveRequestRepo.save(any(LeaveRequest.class))).thenAnswer(i -> i.getArguments()[0]);

        ResponseEntity<Void> response = managerController.rejectLeave("lr-3");

        assertEquals("REJECTED", lr.getStatus());
        assertEquals(204, response.getStatusCodeValue());
        verify(leaveRequestRepo).save(lr);
    }

    @Test
    void testRejectLeave_notFound() {
        when(leaveRequestRepo.findById("lr-100")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> managerController.rejectLeave("lr-100"));
    }
}