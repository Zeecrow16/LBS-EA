//package com.example.enterpriselbs.controller;
//
//import com.example.enterpriselbs.application.controllers.admin.AdminController;
//import com.example.enterpriselbs.infrastructure.entities.Staff;
//import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AdminControllerTest {
//
//    private StaffRepository staffRepo;
//    private AdminController adminController;
//
//    @BeforeEach
//    void setUp() {
//        staffRepo = mock(StaffRepository.class);
//        adminController = new AdminController(staffRepo);
//    }
//
//    @Test
//    void testUpdateDepartment_success() {
//        Staff staff = new Staff();
//        staff.setId("s-1");
//        staff.setDepartmentId("d-1");
//
//        when(staffRepo.findById("s-1")).thenReturn(Optional.of(staff));
//        when(staffRepo.save(any(Staff.class))).thenAnswer(i -> i.getArguments()[0]);
//
//        Staff updated = adminController.updateDepartment("s-1", "d-2");
//
//        assertEquals("d-2", updated.getDepartmentId());
//        verify(staffRepo).save(staff);
//    }
//
//    @Test
//    void testUpdateDepartment_notFound() {
//        when(staffRepo.findById("s-99")).thenReturn(Optional.empty());
//
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
//                () -> adminController.updateDepartment("s-99", "d-2"));
//        assertEquals("404 NOT_FOUND \"Staff not found\"", exception.getMessage());
//    }
//
//    @Test
//    void testAddStaff_success() {
//        Staff staff = new Staff();
//        staff.setId("s-2");
//        staff.setUsername("newstaff");
//
//        when(staffRepo.save(staff)).thenReturn(staff);
//
//        Staff saved = adminController.addStaff(staff);
//
//        assertEquals("s-2", saved.getId());
//        assertEquals("newstaff", saved.getUsername());
//        verify(staffRepo).save(staff);
//    }
//}