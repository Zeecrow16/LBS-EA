package com.example.enterpriselbs.application.controllers.staff;

import com.example.enterpriselbs.infrastructure.entities.LeaveRequest;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/staff")
public class StaffController {

    private final LeaveRequestRepository leaveRequestRepo;

    public StaffController(LeaveRequestRepository leaveRequestRepo) {
        this.leaveRequestRepo = leaveRequestRepo;
    }

    @GetMapping("/leave-requests/{staffId}")
    public List<LeaveRequest> getAllLeaveRequests(@PathVariable String staffId){
        return leaveRequestRepo.findByStaffId(staffId);
    }

    @PostMapping("/leave-request")
    public LeaveRequest requestLeave(@RequestBody LeaveRequest request){
        request.setStatus("PENDING");
        return leaveRequestRepo.save(request);
    }

    @DeleteMapping("/leave-request/{id}")
    public LeaveRequest cancelLeave(@PathVariable String id){
        LeaveRequest lr = leaveRequestRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave request not found"));
        lr.setStatus("CANCELLED");
        return leaveRequestRepo.save(lr);
    }
}