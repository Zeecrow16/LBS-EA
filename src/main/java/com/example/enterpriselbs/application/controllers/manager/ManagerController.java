package com.example.enterpriselbs.application.controllers.manager;

import com.example.enterpriselbs.infrastructure.entities.LeaveRequest;
import com.example.enterpriselbs.infrastructure.entities.Staff;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    private final LeaveRequestRepository leaveRequestRepo;
    private final StaffRepository staffRepo;

    public ManagerController(LeaveRequestRepository leaveRequestRepo, StaffRepository staffRepo) {
        this.leaveRequestRepo = leaveRequestRepo;
        this.staffRepo = staffRepo;
    }
    @GetMapping("/outstanding/{managerId}")
    public List<LeaveRequest> getOutstandingRequests(@PathVariable String managerId) {
        List<Staff> staffList = staffRepo.findByManagerId(managerId);
        List<String> staffIds = new ArrayList<>();
        for (Staff s : staffList) {
            staffIds.add(s.getId());
        }

        List<LeaveRequest> allPending = leaveRequestRepo.findByStatus("PENDING");
        List<LeaveRequest> result = new ArrayList<>();
        for (LeaveRequest lr : allPending) {
            if (staffIds.contains(lr.getStaffId())) {
                result.add(lr);
            }
        }
        return result;
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<Void> approveLeave(@PathVariable String id){
        LeaveRequest lr = leaveRequestRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave not found"));
        lr.setStatus("APPROVED");
        leaveRequestRepo.save(lr);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reject/{id}")
    public ResponseEntity<Void> rejectLeave(@PathVariable String id){
        LeaveRequest lr = leaveRequestRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave not found"));
        lr.setStatus("REJECTED");
        leaveRequestRepo.save(lr);
        return ResponseEntity.noContent().build();
    }
}