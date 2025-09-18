package com.example.enterpriselbs.application.controllers.manager;

import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.springframework.web.bind.annotation.*;

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

    //view outstanding team requests
    @GetMapping("/leave-requests/outstanding")
    public List<LeaveRequestDto> viewOutstandingTeamRequests(@RequestParam String managerId) {
        List<StaffEntity> teamMembers = staffRepo.findByManagerId(managerId);

        return teamMembers.stream()
                .flatMap(staff -> leaveRequestRepo.findByStaffId(staff.getId()).stream())
                .map(entity -> LeaveRequestMapper.toAggregate(entity))
                .filter(agg -> agg.status() == LeaveStatus.PENDING)
                .map(agg -> {
                    LeaveRequestDto dto = new LeaveRequestDto();
                    dto.staffId = agg.staffId().value();
                    dto.startDate = agg.period().startDate();
                    dto.endDate = agg.period().endDate();
                    dto.status = agg.status();
                    return dto;
                })
                .toList();
    }

    //approve leave request
    @PostMapping("/leave-request/{id}/approve")
    public void approveLeave(@PathVariable String id) {
        var leaveRequest = LeaveRequestMapper.toAggregate(
                leaveRequestRepo.findById(id).orElseThrow()
        );
        leaveRequest.approve();
        leaveRequestRepo.save(LeaveRequestMapper.toEntity(leaveRequest));
        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());
    }

    //reject leave request
    @PostMapping("/leave-request/{id}/reject")
    public void rejectLeave(@PathVariable String id) {
        var leaveRequest = LeaveRequestMapper.toAggregate(
                leaveRequestRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Leave request not found"))
        );
        leaveRequest.reject();
        leaveRequestRepo.save(LeaveRequestMapper.toEntity(leaveRequest));
        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());
    }

    //view staff remaining leave
    @GetMapping("/staff/{staffId}/remaining-leave")
    public int viewStaffRemainingLeave(@PathVariable String staffId) {
        StaffEntity staff = staffRepo.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        int approvedLeaveDays = leaveRequestRepo.findByStaffId(staffId).stream()
                .map(LeaveRequestMapper::toAggregate)
                .filter(lr -> lr.status() == LeaveStatus.APPROVED)
                .mapToInt(lr -> lr.period().days())
                .sum();

        return staff.getLeaveAllocation() - approvedLeaveDays;
    }

    //view all leave requests
    @GetMapping("/leave-requests")
    public List<LeaveRequestDto> viewAllLeaveRequests() {
        List<LeaveRequestEntity> leaveEntities = (List<LeaveRequestEntity>) leaveRequestRepo.findAll();

        return leaveEntities.stream()
                .map(e -> {
                    LeaveRequestDto dto = new LeaveRequestDto();
                    dto.staffId = e.getStaffId();
                    dto.startDate = e.getStartDate();
                    dto.endDate = e.getEndDate();
                    dto.status = LeaveStatus.valueOf(e.getStatus());
                    return dto;
                })
                .toList();
    }

}