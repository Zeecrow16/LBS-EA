package com.example.enterpriselbs.application.controllers.manager;

import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
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
    private final LocalDomainEventManager localDomainEventManager;
    private final StaffRepository staffRepo;

    public ManagerController(LeaveRequestRepository leaveRequestRepo,LocalDomainEventManager localDomainEventManager, StaffRepository staffRepo) {
        this.leaveRequestRepo = leaveRequestRepo;
        this.localDomainEventManager = localDomainEventManager;
        this.staffRepo = staffRepo;
    }

    //view team leave


    //approve leave request
    @PostMapping("/leave-request/{id}/approve")
    public void approveLeave(@PathVariable String id) {
        var entity = leaveRequestRepo.findById(id).orElseThrow();
        var agg = LeaveRequestMapper.toAggregate(entity);
        agg.approve();
        leaveRequestRepo.save(LeaveRequestMapper.toEntity(agg));
        localDomainEventManager.manageDomainEvents(this, agg.listOfDomainEvents());
    }

    //reject leave request
    @PostMapping("/leave-request/{id}/reject")
    public void rejectLeave(@PathVariable String id) {
        var entity = leaveRequestRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        var leaveRequest = LeaveRequestMapper.toAggregate(entity);

        leaveRequest.reject();

        leaveRequestRepo.save(LeaveRequestMapper.toEntity(leaveRequest));

        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());
    }


    //view staff leave balance


//    //view outstanding leave requests (team)
//    @GetMapping("/outstanding/{managerId}")
//    public List<LeaveRequestEntity> getOutstandingRequests(@PathVariable String managerId) {
//        List<StaffEntity> staffList = staffRepo.findByManagerId(managerId);
//        List<String> staffIds = new ArrayList<>();
//        for (StaffEntity s : staffList) {
//            staffIds.add(s.getId());
//        }
//
//        List<LeaveRequestEntity> allPending = leaveRequestRepo.findByStatus("PENDING");
//        List<LeaveRequestEntity> result = new ArrayList<>();
//        for (LeaveRequestEntity lr : allPending) {
//            if (staffIds.contains(lr.getStaffId())) {
//                result.add(lr);
//            }
//        }
//        return result;
//    }
//
//    //Approve staff leave requests
//    @PatchMapping("/approve/{id}")
//    public ResponseEntity<Void> approveLeave(@PathVariable String id){
//        LeaveRequestEntity lr = leaveRequestRepo.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave not found"));
//        lr.setStatus("APPROVED");
//        leaveRequestRepo.save(lr);
//        return ResponseEntity.noContent().build();
//    }
//
//    //reject leave request
//    @PatchMapping("/reject/{id}")
//    public ResponseEntity<Void> rejectLeave(@PathVariable String id){
//        LeaveRequestEntity lr = leaveRequestRepo.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave not found"));
//        lr.setStatus("REJECTED");
//        leaveRequestRepo.save(lr);
//        return ResponseEntity.noContent().build();
//    }
//
//    //View staff remaining leave

}