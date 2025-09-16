package com.example.enterpriselbs.application.controllers.admin;

import com.example.enterpriselbs.application.dto.StaffDto;
import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.mappers.StaffMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/staff")
public class AdminController {

    private final LeaveRequestRepository leaveRequestRepo;
    private final LocalDomainEventManager localDomainEventManager;
    private final StaffRepository staffRepo;

    public AdminController(LeaveRequestRepository leaveRequestRepo,LocalDomainEventManager localDomainEventManager, StaffRepository staffRepo) {
        this.leaveRequestRepo = leaveRequestRepo;
        this.localDomainEventManager = localDomainEventManager;
        this.staffRepo = staffRepo;
    }

    //View all leave requests

    //approve leave

    // adjust annual leave allocations

//    //view staff
//    @GetMapping("/{id}")
//    public StaffAggregate getStaff(@PathVariable String id) {
//        return staffRepo.findById(id)
//                .map(StaffMapper::toAggregate)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found"));
//    }

    //add new staff

    //amend role

    //amend department

    //view outstanding leave requests

    //approve leave requests on behalf of managers
    @PostMapping("/leave-request/{id}/approve")
    public void approveLeave(@PathVariable String id) {
        var entity = leaveRequestRepo.findById(id).orElseThrow();
        var agg = LeaveRequestMapper.toAggregate(entity);
        agg.approve();
        leaveRequestRepo.save(LeaveRequestMapper.toEntity(agg));
        localDomainEventManager.manageDomainEvents(this, agg.listOfDomainEvents());
    }

}