package com.example.enterpriselbs.aggregates;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.events.LeaveApprovedEvent;
import com.example.enterpriselbs.domain.events.LeaveCancelledEvent;
import com.example.enterpriselbs.domain.events.LeaveRejectedEvent;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveRequestAggregateTest {

}