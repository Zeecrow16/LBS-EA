package com.example.enterpriselbs.aggregates;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.aggregates.factory.UniqueIdFactory;
import com.example.enterpriselbs.domain.events.LocalEvent;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveRequestAggregateTest {
    private final Identity STAFF_ID = UniqueIdFactory.createID();
    private final Identity LEAVE_ID = UniqueIdFactory.createID();
    private final LeavePeriod PERIOD = new LeavePeriod(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));

    private LeaveRequestAggregate createValidLeaveRequest() {
        return LeaveRequestAggregate.leaveRequestOfWithEvent(LEAVE_ID, STAFF_ID, PERIOD);
    }

    @Test
    @DisplayName("LeaveRequestAggregate creation does not throw")
    void testCreation() {
        assertDoesNotThrow(this::createValidLeaveRequest);
    }

    @Test
    @DisplayName("LeaveRequestAggregate has correct staffId and period")
    void testStaffIdAndPeriod() {
        LeaveRequestAggregate leave = createValidLeaveRequest();
        assertEquals(STAFF_ID, leave.staffId());
        assertEquals(PERIOD, leave.period());
        assertEquals(LeaveStatus.PENDING, leave.status());
    }

    @Test
    @DisplayName("LeaveRequestAggregate approve changes status and adds event")
    void testApproveLeave() {
        LeaveRequestAggregate leave = createValidLeaveRequest();

        leave.approve();

        assertEquals(LeaveStatus.APPROVED, leave.status());
        List<LocalEvent> events = leave.listOfDomainEvents();
        assertTrue(events.stream().anyMatch(e -> e.getClass().getSimpleName().equals("LeaveApprovedEvent")));
    }

    @Test
    @DisplayName("LeaveRequestAggregate reject changes status and adds event")
    void testRejectLeave() {
        LeaveRequestAggregate leave = createValidLeaveRequest();

        leave.reject();

        assertEquals(LeaveStatus.REJECTED, leave.status());
        List<LocalEvent> events = leave.listOfDomainEvents();
        assertTrue(events.stream().anyMatch(e -> e.getClass().getSimpleName().equals("LeaveRejectedEvent")));
    }

    @Test
    @DisplayName("LeaveRequestAggregate cancel changes status and adds event")
    void testCancelLeave() {
        LeaveRequestAggregate leave = createValidLeaveRequest();

        leave.cancel();

        assertEquals(LeaveStatus.CANCELLED, leave.status());
        List<LocalEvent> events = leave.listOfDomainEvents();
        assertTrue(events.stream().anyMatch(e -> e.getClass().getSimpleName().equals("LeaveCancelledEvent")));
    }

    @Test
    @DisplayName("Cannot approve non-pending leave")
    void testApproveNonPendingThrows() {
        LeaveRequestAggregate leave = createValidLeaveRequest();
        leave.approve();

        assertThrows(IllegalStateException.class, leave::approve);
    }

    @Test
    @DisplayName("Cannot reject non-pending leave")
    void testRejectNonPendingThrows() {
        LeaveRequestAggregate leave = createValidLeaveRequest();
        leave.approve();

        assertThrows(IllegalStateException.class, leave::reject);
    }

    @Test
    @DisplayName("Cannot cancel twice")
    void testCancelTwiceThrows() {
        LeaveRequestAggregate leave = createValidLeaveRequest();
        leave.cancel();

        assertThrows(IllegalStateException.class, leave::cancel);
    }

    @Test
    @DisplayName("LeaveRequestAggregate domain events contain LeaveRequestedEvent")
    void testDomainEventsOnCreation() {
        LeaveRequestAggregate leave = createValidLeaveRequest();
        List<LocalEvent> events = leave.listOfDomainEvents();
        assertTrue(events.stream().anyMatch(e -> e.getClass().getSimpleName().equals("LeaveRequestedEvent")));
    }

}