package com.example.enterpriselbs.application.controllers.staff;

import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.aggregates.factory.LeaveRequestFactoryInterface;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/staff")
public class StaffController {

    private final LeaveRequestRepository leaveRequestRepo;
    private final LocalDomainEventManager localDomainEventManager;
    private final LeaveRequestFactoryInterface leaveRequestFactory;

    public StaffController(LeaveRequestRepository leaveRequestRepo,
                                  LocalDomainEventManager localDomainEventManager,
                                  LeaveRequestFactoryInterface leaveRequestFactory) {
        this.leaveRequestRepo = leaveRequestRepo;
        this.localDomainEventManager = localDomainEventManager;
        this.leaveRequestFactory = leaveRequestFactory;
    }

    //send leave request
    @PostMapping("/leave-request")
    public String requestLeave(@RequestBody LeaveRequestDto dto) {
        LeavePeriod period = new LeavePeriod(dto.startDate, dto.endDate);

        LeaveRequestAggregate leaveRequest = leaveRequestFactory.create(new Identity(dto.staffId), period);

        var entity = LeaveRequestMapper.toEntity(leaveRequest);
        leaveRequestRepo.save(entity);

        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());

        return leaveRequest.id().value();
    }

    // cancel leave request
    @PostMapping("/leave-request/{id}/cancel")
    public String cancelLeave(@PathVariable String id) {
        var optional = leaveRequestRepo.findById(id);
        if (optional.isEmpty()) {
            return "Leave request not found";
        }

        LeaveRequestAggregate leaveRequest = LeaveRequestMapper.toAggregate(optional.get());
        leaveRequest.cancel();

        leaveRequestRepo.save(LeaveRequestMapper.toEntity(leaveRequest));
        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());

        return "Leave request cancelled: " + id;
    }

    // view leave request


    // view remaining leave
    @GetMapping("/{staffId}/leave-remaining")
    public int remainingLeave(@PathVariable String staffId) {
        var entities = leaveRequestRepo.findByStaffId(staffId);
        int usedDays = entities.stream()
                .map(LeaveRequestMapper::toAggregate)
                .mapToInt(agg -> agg.period().days())
                .sum();

        int totalAnnualLeave = 20;
        return totalAnnualLeave - usedDays;
    }


}
