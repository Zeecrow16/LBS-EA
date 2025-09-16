package com.example.enterpriselbs.domain.aggregates;

import com.example.enterpriselbs.domain.BaseEntity;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.events.LeaveApprovedEvent;
import com.example.enterpriselbs.domain.events.LeaveCancelledEvent;
import com.example.enterpriselbs.domain.events.LeaveRejectedEvent;
import com.example.enterpriselbs.domain.events.LocalEvent;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;

import java.util.ArrayList;
import java.util.List;

public class LeaveRequestAggregate extends BaseEntity {
    private final Identity id;
    private final Identity staffId;
    private final LeavePeriod period;
    private LeaveStatus status;
    private final List<LocalEvent> domainEvents = new ArrayList<>();

    public LeaveRequestAggregate(Identity staffId, LeavePeriod period) {
        super(new Identity(java.util.UUID.randomUUID().toString()));
        this.id = new Identity(java.util.UUID.randomUUID().toString());
        this.staffId = staffId;
        this.period = period;
        this.status = LeaveStatus.PENDING;
    }

    public LeaveRequestAggregate(Identity id, Identity staffId, LeavePeriod period, LeaveStatus status) {
        super(id);
        this.id = id;
        this.staffId = staffId;
        this.period = period;
        this.status = status;
    }

    public Identity id() { return id; }
    public Identity staffId() { return staffId; }
    public LeavePeriod period() { return period; }
    public LeaveStatus status() { return status; }
    public List<LocalEvent> listOfDomainEvents() { return new ArrayList<>(domainEvents); }
    public void addDomainEvent(LocalEvent event) { domainEvents.add(event); }

    public void approve() {
        if(status != LeaveStatus.PENDING) throw new IllegalStateException("Only pending leave can be approved");
        status = LeaveStatus.APPROVED;
        addDomainEvent(new LeaveApprovedEvent(this));
    }

    public void reject() {
        if(status != LeaveStatus.PENDING) throw new IllegalStateException("Only pending leave can be rejected");
        status = LeaveStatus.REJECTED;
        addDomainEvent(new LeaveRejectedEvent(this));
    }

    public void cancel() {
        if(status == LeaveStatus.CANCELLED) throw new IllegalStateException("Already cancelled");
        status = LeaveStatus.CANCELLED;
        addDomainEvent(new LeaveCancelledEvent(this));
    }
}