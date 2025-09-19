package com.example.enterpriselbs.infrastructure.mappers;

import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.domain.events.LeaveRequestedEvent;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

//@Component
//@AllArgsConstructor
//public class LeaveEventMapper {
//    private final StaffRepository staffRepository;
//
//    public StaffAggregate getStaffFromEvent(LeaveRequestedEvent event){
//        return staffRepository.findById(event.staffId())
//                .map(StaffMapper::toDomain)
//                .orElseThrow(() -> new IllegalArgumentException(
//                        "Staff not found for LeaveRequestedEvent: " + event.staffId()
//                ));
//    }
//}
