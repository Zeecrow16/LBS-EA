package com.example.enterpriselbs.application.services.applicationService;

import com.example.enterpriselbs.application.dto.StaffDto;
import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
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
//
//    @Transactional
//    public String addNewStaff(StaffDto dto) {
//        Identity staffId = new Identity(dto.getId());
//        LOG.info("Adding new staff with id {}", staffId);
//
//        StaffAggregate staff = new StaffAggregate(
//                staffId,
//                dto.getUsername(),
//                new FullName(dto.getFirstName(), dto.getSurname()),
//                dto.getRole(),
//                dto.getManagerId() != null ? new Identity(dto.getManagerId()) : null,
//                dto.getDepartmentId() != null ? new Identity(dto.getDepartmentId()) : null,
//                dto.getLeaveAllocation(),
//                new Password(dto.getPasswordHash())
//        );
//
//        StaffEntity entity = StaffMapper.toJpa(staff);
//        staffRepository.save(entity);
//
//        localDomainEventManager.manageDomainEvents(this, List.of(new StaffAddedEvent(staff)));
//
//        return staffId.value();
//    }
//
//    @Transactional
//    public void amendStaffRole(String staffId, Role newRole) {
//        StaffAggregate staff = findStaffAggregate(staffId);
//        staff.changeRole(newRole);
//
//        staffRepository.save(StaffMapper.toJpa(staff));
//        localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());
//    }
//
//    @Transactional
//    public void amendStaffDepartment(String staffId, String newDepartmentId) {
//        StaffAggregate staff = findStaffAggregate(staffId);
//        staff.changeDepartment(new Identity(newDepartmentId));
//
//        staffRepository.save(StaffMapper.toJpa(staff));
//        localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());
//    }
//
//    @Transactional
//    public void adjustLeaveAllocation(String staffId, int newAllocation) {
//        StaffAggregate staff = findStaffAggregate(staffId);
//        staff.updateLeaveAllocation(newAllocation);
//
//        staffRepository.save(StaffMapper.toJpa(staff));
//        localDomainEventManager.manageDomainEvents(this, staff.listOfDomainEvents());
//    }
//
//    @Transactional
//    public void approveLeaveRequest(String leaveRequestId) {
//        LeaveRequestAggregate leaveRequest = findLeaveRequestAggregate(leaveRequestId);
//        leaveRequest.approve();
//
//        leaveRequestRepository.save(LeaveRequestMapper.toJpa(leaveRequest));
//        localDomainEventManager.manageDomainEvents(this, leaveRequest.listOfDomainEvents());
//    }
//
//    private StaffAggregate findStaffAggregate(String staffId) {
//        Optional<StaffEntity> entity = staffRepository.findById(staffId);
//        if (entity.isEmpty()) throw new IllegalArgumentException("Staff id not found");
//        return StaffMapper.toDomain(entity.get());
//    }
//
//    private LeaveRequestAggregate findLeaveRequestAggregate(String leaveRequestId) {
//        Optional<?> entity = leaveRequestRepository.findById(leaveRequestId);
//        if (entity.isEmpty()) throw new IllegalArgumentException("Leave request id not found");
//        return LeaveRequestMapper.toDomain((com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity) entity.get());
//    }
}