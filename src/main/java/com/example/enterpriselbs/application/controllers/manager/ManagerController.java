package com.example.enterpriselbs.application.controllers.manager;

import com.example.enterpriselbs.application.QueryHandlers.ManagerQueryHandler;
import com.example.enterpriselbs.application.commands.ApproveLeaveCommand;
import com.example.enterpriselbs.application.commands.RejectLeaveCommand;
import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.application.services.applicationService.ManagerApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@AllArgsConstructor
public class ManagerController {

    private final ManagerApplicationService managerService;
    private final ManagerQueryHandler managerQueryHandler;

    //QUERIES
    //View outstanding requests for individual staff member
    @GetMapping("/{staffId}/outstanding-requests")
    public List<LeaveRequestDto> viewOutstandingRequestsForStaff(@PathVariable String staffId) {
        List<LeaveRequestDto> requests = managerQueryHandler.findOutstandingRequestsForStaff(staffId);
        if (requests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No outstanding requests found");
        }
        return requests;
    }


    //View amount of annual leave for a staff member
    @GetMapping("/{staffId}/remaining-leave")
    public int viewRemainingLeaveForStaff(@PathVariable String staffId) {
        return managerQueryHandler.remainingLeaveForStaff(staffId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff id not found"));
    }


    //COMMANDS
    //Approve annual leave
    @PatchMapping("/approve-leave")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void approveLeave(@RequestBody ApproveLeaveCommand command) {
        managerService.approveLeave(command.getLeaveRequestId());
    }

    //Reject leave request
    @PatchMapping("/reject-leave")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void rejectLeave(@RequestBody RejectLeaveCommand command) {
        managerService.rejectLeave(command.getLeaveRequestId());
    }

}