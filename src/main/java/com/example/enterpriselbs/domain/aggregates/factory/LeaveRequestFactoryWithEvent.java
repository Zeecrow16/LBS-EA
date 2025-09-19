package com.example.enterpriselbs.domain.aggregates.factory;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.events.LeaveRequestedEvent;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import org.springframework.stereotype.Component;

//@Component
//public class LeaveRequestFactoryWithEvent implements LeaveRequestFactoryInterface {
//    @Override
//    public LeaveRequestAggregate create(Identity staffId, LeavePeriod period) {
//        LeaveRequestAggregate leaveRequest = new LeaveRequestAggregate(new Identity(java.util.UUID.randomUUID().toString()), staffId, period);
//        leaveRequest.addDomainEvent(new LeaveRequestedEvent(leaveRequest));
//        return leaveRequest;
//    }
//}