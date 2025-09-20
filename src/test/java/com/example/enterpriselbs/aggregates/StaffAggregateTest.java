package com.example.enterpriselbs.aggregates;

import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.domain.aggregates.factory.UniqueIdFactory;
import com.example.enterpriselbs.domain.valueObjects.FullName;
import com.example.enterpriselbs.domain.valueObjects.Password;
import com.example.enterpriselbs.domain.valueObjects.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaffAggregateTest {
    private final Identity VALID_ID = UniqueIdFactory.createID();
    private final String VALID_USERNAME = "o.butler";
    private final FullName VALID_NAME = new FullName("Octavia", "Butler");
    private final Role VALID_ROLE = Role.STAFF;
    private final Identity VALID_MANAGER_ID = UniqueIdFactory.createID();
    private final Identity VALID_DEPARTMENT_ID = UniqueIdFactory.createID();
    private final int VALID_ALLOCATION = 20;
    private final Password VALID_PASSWORD = new Password("Secret123!");

    private StaffAggregate createValidStaff() {
        return StaffAggregate.createWithEvent(
                VALID_ID,
                VALID_USERNAME,
                VALID_NAME,
                VALID_ROLE,
                VALID_MANAGER_ID,
                VALID_DEPARTMENT_ID,
                VALID_ALLOCATION,
                VALID_PASSWORD
        );
    }

    @Test
    @DisplayName("StaffAggregate creation does not throw")
    void testCreation() {
        assertDoesNotThrow(this::createValidStaff);
    }

    @Test
    @DisplayName("StaffAggregate leave allocation is correct")
    void testLeaveAllocation() {
        StaffAggregate staff = createValidStaff();
        assertEquals(VALID_ALLOCATION, staff.leaveAllocation());
    }

    @Test
    @DisplayName("StaffAggregate full name is correct")
    void testFullName() {
        StaffAggregate staff = createValidStaff();
        assertEquals("Octavia", staff.fullName().firstName());
        assertEquals("Butler", staff.fullName().surname());
    }

    @Test
    @DisplayName("StaffAggregate role and username are correct")
    void testRoleAndUsername() {
        StaffAggregate staff = createValidStaff();
        assertEquals(VALID_ROLE, staff.role());
        assertEquals(VALID_USERNAME, staff.username());
    }

    @Test
    @DisplayName("StaffAggregate password is correct")
    void testPassword() {
        StaffAggregate staff = createValidStaff();
        assertEquals(VALID_PASSWORD, staff.password());
    }
}
