package com.example.enterpriselbs.application.controllers.admin;

import com.example.enterpriselbs.application.QueryHandlers.AdminQueryHandler;
import com.example.enterpriselbs.application.commands.*;
import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.application.services.applicationService.AdminApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminApplicationService adminService;
    private final AdminQueryHandler queryHandler;

    //QUERIES
    //View all outstanding leave requests (filtered by staff member, manager team, across company)
    @GetMapping("/leave-requests")
    public List<LeaveRequestDto> viewOutstandingLeaveRequests(
            @RequestParam(required = false) String staffId,
            @RequestParam(required = false) String managerId
    ) {
        List<LeaveRequestDto> requests = queryHandler.findOutstandingLeaveRequests(staffId, managerId);
        if (requests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No outstanding leave requests found");
        }
        return requests;
    }

    // COMMANDS
    //Add new staff
    @PostMapping("/add-staff")
    @ResponseStatus(HttpStatus.CREATED)
    public String addStaff(@RequestBody AddStaffCommand cmd) {
        return adminService.addStaff(cmd);
    }

    //Amend the role
    @PutMapping("/amend-role")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void amendRole(@RequestBody AmendRoleCommand cmd) {
        adminService.amendRole(cmd);
    }

    //Amend the department
    @PutMapping("/amend-department")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void amendDepartment(@RequestBody AmendDepartmentCommand cmd) {
        adminService.amendDepartment(cmd);
    }

    //Amend the amount of annual leave assigned to staff
    @PutMapping("/amend-leave-allocation")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void amendLeaveAllocation(@RequestBody AmendAnnualLeaveCommand cmd) {
        adminService.amendLeaveAllocation(cmd);
    }

    //Approve leave requests on behalf of managers
    @PatchMapping("/approve-leave")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void approveLeave(@RequestBody AdminApproveLeaveCommand cmd) {
        adminService.approveLeave(cmd);
    }

}




