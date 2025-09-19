package com.example.enterpriselbs.application.services.applicationService;

import com.example.enterpriselbs.application.commands.*;
import com.example.enterpriselbs.application.dto.StaffDto;
import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.domain.aggregates.factory.UniqueIdFactory;
import com.example.enterpriselbs.domain.events.StaffAddedEvent;
import com.example.enterpriselbs.domain.valueObjects.FullName;
import com.example.enterpriselbs.domain.valueObjects.Role;
import com.example.enterpriselbs.domain.valueObjects.Password;
import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import com.example.enterpriselbs.infrastructure.mappers.StaffMapper;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminApplicationService {

    private final StaffRepository staffRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LocalDomainEventManager localDomainEventManager;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Transactional
    public String addStaff(AddStaffCommand cmd) {
        LOG.info("Attempting to add new staff: {}", cmd.getUsername());
        StaffAggregate staff = StaffAggregate.createWithEvent(
                UniqueIdFactory.createID(),
                cmd.getUsername(),
                new FullName(cmd.getFirstName(), cmd.getSurname()),
                cmd.getRole(),
                cmd.getManagerId() != null ? new Identity(cmd.getManagerId()) : null,
                cmd.getDepartmentId() != null ? new Identity(cmd.getDepartmentId()) : null,
                cmd.getLeaveAllocation(),
                new Password(cmd.getPassword())
        );
        staffRepository.save(StaffMapper.toJpa(staff));
        LOG.info("Staff created with ID: {}", staff.id().value());
        localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());
        return staff.id().value();
    }

    @Transactional
    public void amendRole(AmendRoleCommand cmd) {
        LOG.info("Amending role for staffId={} to {}", cmd.getStaffId(), cmd.getNewRole());
        staffRepository.findById(cmd.getStaffId())
                .map(StaffMapper::toDomain)
                .ifPresentOrElse(staff -> {
                    staff.changeRole(cmd.getNewRole());
                    staffRepository.save(StaffMapper.toJpa(staff));
                    LOG.info("Role updated for staffId={}", staff.id().value());
                    localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());
                }, () -> {
                    LOG.error("Staff not found for ID: {}", cmd.getStaffId());
                    throw new IllegalArgumentException("Staff not found"); });
    }

    @Transactional
    public void amendDepartment(AmendDepartmentCommand cmd) {
        LOG.info("Amending department for staffId={} to {}", cmd.getStaffId(), cmd.getNewDepartmentId());
        staffRepository.findById(cmd.getStaffId())
                .map(StaffMapper::toDomain)
                .ifPresentOrElse(staff -> {
                    staff.changeDepartment(new Identity(cmd.getNewDepartmentId()));
                    staffRepository.save(StaffMapper.toJpa(staff));
                    LOG.info("Department updated for staffId={}", staff.id().value());
                    localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());
                }, () -> {
                    LOG.error("Staff not found for ID: {}", cmd.getStaffId());
                    throw new IllegalArgumentException("Staff not found"); });
    }

    @Transactional
    public void amendLeaveAllocation(AmendAnnualLeaveCommand cmd) {
        staffRepository.findById(cmd.getStaffId())
                .map(StaffMapper::toDomain)
                .ifPresentOrElse(staff -> {
                    staff.updateLeaveAllocation(cmd.getNewLeaveAllocation());
                    staffRepository.save(StaffMapper.toJpa(staff));
                    LOG.info("Leave allocation updated for staffId={}", staff.id().value());
                    localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());
                }, () -> {
                    LOG.error("Staff not found for ID: {}", cmd.getStaffId());
                    throw new IllegalArgumentException("Staff not found"); });
    }

    @Transactional
    public void approveLeave(AdminApproveLeaveCommand cmd) {
        leaveRequestRepository.findById(cmd.getLeaveRequestId())
                .map(LeaveRequestMapper::toDomain)
                .ifPresentOrElse(request -> {
                    request.approve();
                    leaveRequestRepository.save(LeaveRequestMapper.toJpa(request));
                    LOG.info("Leave approved for requestId={}", request.id().value());
                   localDomainEventManager.manageDomainEvents(this, request.listOfDomainEvents());
                }, () -> {
                    LOG.error("Leave request not found for ID: {}", cmd.getLeaveRequestId());
                    throw new IllegalArgumentException("Leave request not found"); });
    }

}