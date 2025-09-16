//package com.example.enterpriselbs.controller;
//
//import com.example.enterpriselbs.application.controllers.staff.StaffController;
//import com.example.enterpriselbs.application.dto.LeaveRequestDto;
//import com.example.enterpriselbs.domain.aggregates.LeaveRequest;
//import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
//import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
//import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
//import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//class StaffControllerTest {
//
//    private LeaveRequestRepository leaveRequestRepo;
//    private StaffRepository staffRepo;
//    private StaffController staffController;
//
//    @BeforeEach
//    void setUp() {
//        leaveRequestRepo = mock(LeaveRequestRepository.class);
//        staffRepo = mock(StaffRepository.class);
//        staffController = new StaffController(leaveRequestRepo, staffRepo); // fixed constructor
//    }
//
//    @Test
//    void testGetAllLeaveRequests() {
//        LeaveRequestEntity entity = new LeaveRequestEntity();
//        entity.setId("lr-1");
//        entity.setStaffId("s-1");
//        entity.setStartDate(LocalDate.of(2025, 9, 20));
//        entity.setEndDate(LocalDate.of(2025, 9, 22));
//        entity.setStatus("PENDING");
//
//        when(leaveRequestRepo.findByStaffId("s-1")).thenReturn(Arrays.asList(entity));
//
//        List<LeaveRequest> result = staffController.getAllLeaveRequests("s-1");
//
//        assertEquals(1, result.size());
//        LeaveRequest lr = result.get(0);
//        assertEquals("lr-1", lr.id().value());
//        assertEquals("s-1", lr.staffId().value());
//        assertEquals(LeaveStatus.PENDING, lr.status());
//    }
//
//    @Test
//    void testRequestLeave() {
//        LeaveRequestDto dto = new LeaveRequestDto();
//        dto.staffId = "s-1";
//        dto.startDate = LocalDate.of(2025, 9, 25);
//        dto.endDate = LocalDate.of(2025, 9, 26);
//
//        when(leaveRequestRepo.save(any(LeaveRequestEntity.class))).thenAnswer(i -> i.getArguments()[0]);
//
//        LeaveRequest saved = staffController.requestLeave(dto);
//
//        assertEquals("s-1", saved.staffId().value());
//        assertEquals(LeaveStatus.PENDING, saved.status());
//    }
//
//    @Test
//    void testCancelLeave_found() {
//        LeaveRequestEntity entity = new LeaveRequestEntity();
//        entity.setId("lr-3");
//        entity.setStaffId("s-1");
//        entity.setStartDate(LocalDate.of(2025, 9, 20));
//        entity.setEndDate(LocalDate.of(2025, 9, 22));
//        entity.setStatus("PENDING");
//
//        when(leaveRequestRepo.findById("lr-3")).thenReturn(Optional.of(entity));
//        when(leaveRequestRepo.save(any(LeaveRequestEntity.class))).thenAnswer(i -> i.getArguments()[0]);
//
//        LeaveRequest cancelled = staffController.cancelLeave("lr-3");
//
//        assertEquals("lr-3", cancelled.id().value());
//        assertEquals(LeaveStatus.CANCELLED, cancelled.status());
//    }
//
//    @Test
//    void testCancelLeave_notFound() {
//        when(leaveRequestRepo.findById("lr-999")).thenReturn(Optional.empty());
//
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
//            staffController.cancelLeave("lr-999");
//        });
//
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
//    }
//}