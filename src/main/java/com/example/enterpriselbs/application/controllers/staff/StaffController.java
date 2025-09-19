package com.example.enterpriselbs.application.controllers.staff;

import com.example.enterpriselbs.application.QueryHandlers.StaffQueryHandler;
import com.example.enterpriselbs.application.commands.CancelLeaveCommand;
import com.example.enterpriselbs.application.commands.RequestLeaveCommand;
import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.application.services.applicationService.StaffApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/staff")
@AllArgsConstructor
public class StaffController {

    private final StaffApplicationService staffService;
    private final StaffQueryHandler staffQueryHandler;

    //COMMANDS

    //Request leave
    @PostMapping("/request-leave")
    @ResponseStatus(HttpStatus.CREATED)
    public String requestLeave(@RequestBody RequestLeaveCommand command) {
        return staffService.requestLeave(command.getStaffId(), command.getStartDate(), command.getEndDate());
    }
    //Cancel leave request
    @DeleteMapping("/cancel-leave")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancelLeaveRequest(@RequestBody CancelLeaveCommand command) {
        staffService.cancelLeaveRequest(command.getLeaveRequestId());
    }

    //QUERIES

    //View status of leave request
    @GetMapping("/{staffId}/leave-status")
    public List<LeaveRequestDto> viewLeaveStatus(@PathVariable String staffId) {
        List<LeaveRequestDto> requests = staffQueryHandler.findLeaveRequests(staffId);
        if (requests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No leave requests found");
        }
        return requests;
    }

    //View remaining annual leave
    @GetMapping("/{staffId}/remaining-leave")
    public int getRemainingLeave(@PathVariable String staffId) {
        try {
            return staffQueryHandler.findRemainingLeave(staffId);
        } catch (IllegalArgumentException iae) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff id not found");
        }
    }


}
