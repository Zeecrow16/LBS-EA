package com.example.enterpriselbs.application.controllers.admin;

import com.example.enterpriselbs.application.dto.*;
import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.factory.DepartmentFactory;
import com.example.enterpriselbs.domain.aggregates.factory.StaffFactory;
import com.example.enterpriselbs.domain.valueObjects.*;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
import com.example.enterpriselbs.infrastructure.mappers.DepartmentMapper;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.mappers.StaffMapper;
import com.example.enterpriselbs.infrastructure.repositories.DepartmentRepository;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final LeaveRequestRepository leaveRequestRepo;
    private final LocalDomainEventManager localDomainEventManager;
    private final StaffRepository staffRepo;
    private final StaffFactory staffFactory;
    private final DepartmentFactory deptFactory;
    private final DepartmentRepository deptRepo;

    public AdminController(LeaveRequestRepository leaveRequestRepo,
                           LocalDomainEventManager localDomainEventManager,
                           StaffRepository staffRepo,
                           StaffFactory staffFactory,
                           DepartmentFactory deptFactory,
                           DepartmentRepository deptRepo) {
        this.leaveRequestRepo = leaveRequestRepo;
        this.localDomainEventManager = localDomainEventManager;
        this.staffRepo = staffRepo;
        this.staffFactory = staffFactory;
        this.deptFactory = deptFactory;
        this.deptRepo = deptRepo;
    }

    //add new staff
    @PostMapping("/add-staff")
    public String addStaff(@RequestBody StaffDto dto) {
        var staff = staffFactory.createNewStaff(
                dto.username,
                new FullName(dto.firstName, dto.surname),
                dto.role,
                dto.managerId != null ? new Identity(dto.managerId) : null,
                dto.departmentId != null ? new Identity(dto.departmentId) : null,
                dto.leaveAllocation,
                new Password(dto.passwordHash)
        );

        staffRepo.save(StaffMapper.toEntity(staff));
        localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());

        return staff.id().value();
    }

    //amend department
    @PostMapping("/change-department")
    public String changeDepartment(@RequestBody ChangeDepartmentDto dto) {
        var staffEntity = staffRepo.findById(dto.staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        var staff = StaffMapper.toAggregate(staffEntity);
        staff.changeDepartment(new Identity(dto.newDepartmentId));

        staffRepo.save(StaffMapper.toEntity(staff));
        localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());

        return "Department updated for staff: " + staff.id().value();
    }

    //amend role
    @PostMapping("/amend-role")
    public String amendRole(@RequestBody AmendRoleDto dto) {
        var staffEntity = staffRepo.findById(dto.staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        var staff = StaffMapper.toAggregate(staffEntity);

        staff.changeRole(dto.newRole);

        staffRepo.save(StaffMapper.toEntity(staff));

        localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());

        return "Role updated for staff: " + staff.id().value();
    }

    //view outstanding leave requests
    @GetMapping("/leave-requests/outstanding")
    public List<LeaveRequestDto> viewOutstandingLeaveRequests() {
        List<LeaveRequestEntity> entities = leaveRequestRepo.findByStatus("PENDING");

        return entities.stream().map(e -> {
            LeaveRequestDto dto = new LeaveRequestDto();
            dto.staffId = e.getStaffId();
            dto.startDate = e.getStartDate();
            dto.endDate = e.getEndDate();
            dto.status = LeaveStatus.valueOf(e.getStatus().toUpperCase());
            return dto;
        }).toList();
    }

    //View all leave requests
    @GetMapping("/leave-requests/view-all")
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

    //adjust annual leave allocations
    @PostMapping("/update-leave-allocation")
    public String updateLeave(@RequestBody UpdateLeaveAllocationDto dto) {
        var staffEntity = staffRepo.findById(dto.staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        var staff = StaffMapper.toAggregate(staffEntity);
        staff.updateLeaveAllocation(dto.newAllocation);

        staffRepo.save(StaffMapper.toEntity(staff));
        localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());

        return "Leave allocation updated for staff: " + staff.id().value();
    }

    //approve leave requests on behalf of managers
    @PostMapping("/leave-request/{id}/approve")
    public void approveLeave(@PathVariable String id) {
        var entity = leaveRequestRepo.findById(id).orElseThrow();
        var agg = LeaveRequestMapper.toAggregate(entity);
        agg.approve();
        leaveRequestRepo.save(LeaveRequestMapper.toEntity(agg));
        localDomainEventManager.manageDomainEvents(this, agg.listOfDomainEvents());
    }

    //Add new department (for testing purposes)
    @PostMapping("/add-department")
    public String addDepartment(@RequestBody DepartmentDto dto) {
        var departmentName = new DepartmentName(dto.name());

        var dept = deptFactory.create(departmentName);

        deptRepo.save(DepartmentMapper.toEntity(dept));

        localDomainEventManager.manageDomainEvents(this, dept.listOfDomainEvents());

        return dept.id().value();
    }

}




