package com.example.enterpriselbs.domain.aggregates;

import com.example.enterpriselbs.domain.BaseEntity;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.events.*;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaveRequestAggregate extends BaseEntity {
    private final Identity id;
    private Identity staffId;
    private LeavePeriod period;
    private LeaveStatus status;
    private final List<LocalEvent> domainEvents = new ArrayList<>();

    private LeaveRequestAggregate(Identity id, Identity staffId, LeavePeriod period) {
        super(id);
        setStaffId(staffId);
        setPeriod(period);
        this.id = id;
        this.status = LeaveStatus.PENDING;
    }

    public static LeaveRequestAggregate leaveRequestOfWithEvent(Identity id, Identity staffId, LeavePeriod period) {
        LeaveRequestAggregate request = new LeaveRequestAggregate(id, staffId, period);
        request.addDomainEvent(new LeaveRequestedEvent(request));
        return request;
    }
    public static LeaveRequestAggregate leaveRequestOf(Identity id, Identity staffId, LeavePeriod period,
                                                       LeaveStatus status, String descriptionOfStatus) {
        LeaveRequestAggregate request = new LeaveRequestAggregate(id, staffId, period);
        request.status = status;
        return request;
    }

    private void setStaffId(Identity staffId) {
        assertArgumentNotNull(staffId, "StaffId cannot be null");
        this.staffId = staffId;
    }

    private void setPeriod(LeavePeriod period) {
        assertArgumentNotNull(period, "Leave period cannot be null");
        this.period = period;
    }

    public Identity id() { return id; }
    public Identity staffId() { return staffId; }
    public LeavePeriod period() { return period; }
    public LeaveStatus status() { return status; }

    public List<LocalEvent> listOfDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    private void addDomainEvent(LocalEvent event) {
        domainEvents.add(event);
    }

    public void approve() {
        if (status != LeaveStatus.PENDING)
            throw new IllegalStateException("Only pending leave can be approved");
        status = LeaveStatus.APPROVED;
        addDomainEvent(new LeaveApprovedEvent(this));
    }

    public void reject() {
        if (status != LeaveStatus.PENDING)
            throw new IllegalStateException("Only pending leave can be rejected");
        status = LeaveStatus.REJECTED;
        addDomainEvent(new LeaveRejectedEvent(this));
    }

    public void cancel() {
        if (status == LeaveStatus.CANCELLED)
            throw new IllegalStateException("Already cancelled");
        status = LeaveStatus.CANCELLED;
        addDomainEvent(new LeaveCancelledEvent(this));
    }
}
