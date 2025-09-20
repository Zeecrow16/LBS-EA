package com.example.enterpriselbs.mappers;

import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
import com.example.enterpriselbs.domain.valueObjects.LeaveStatus;
import com.example.enterpriselbs.infrastructure.entities.LeaveRequestEntity;
import com.example.enterpriselbs.infrastructure.mappers.LeaveRequestMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaveRequestMapperTest {
    @Test
    @DisplayName("toDomain maps LeaveRequestEntity to LeaveRequestAggregate correctly")
    void testToDomain() {
        LeaveRequestEntity entity = new LeaveRequestEntity();
        entity.setId("LR001");
        entity.setStaffId("STAFF001");
        entity.setStartDate(LocalDate.of(2025, 9, 20));
        entity.setEndDate(LocalDate.of(2025, 9, 22));
        entity.setStatus("APPROVED");

        LeaveRequestAggregate aggregate = LeaveRequestMapper.toDomain(entity);

        assertEquals("LR001", aggregate.id().value());
        assertEquals("STAFF001", aggregate.staffId().value());
        assertEquals(LocalDate.of(2025, 9, 20), aggregate.period().startDate());
        assertEquals(LocalDate.of(2025, 9, 22), aggregate.period().endDate());
        assertEquals(LeaveStatus.APPROVED, aggregate.status());
    }

    @Test
    @DisplayName("toJpa maps LeaveRequestAggregate to LeaveRequestEntity correctly")
    void testToJpa() {
        LeaveRequestAggregate aggregate = LeaveRequestAggregate.leaveRequestOf(
                new Identity("LR002"),
                new Identity("STAFF002"),
                new LeavePeriod(LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 3)),
                LeaveStatus.PENDING,
                "Test"
        );

        LeaveRequestEntity entity = LeaveRequestMapper.toJpa(aggregate);

        assertEquals("LR002", entity.getId());
        assertEquals("STAFF002", entity.getStaffId());
        assertEquals(LocalDate.of(2025, 10, 1), entity.getStartDate());
        assertEquals(LocalDate.of(2025, 10, 3), entity.getEndDate());
        assertEquals("PENDING", entity.getStatus());
    }

    @Test
    @DisplayName("toDTO maps LeaveRequestEntity to LeaveRequestDto correctly")
    void testToDTO() {
        LeaveRequestEntity entity = new LeaveRequestEntity();
        entity.setId("LR003");
        entity.setStaffId("STAFF003");
        entity.setStartDate(LocalDate.of(2025, 11, 5));
        entity.setEndDate(LocalDate.of(2025, 11, 6));
        entity.setStatus("REJECTED");

        LeaveRequestDto dto = LeaveRequestMapper.toDTO(entity);

        assertEquals("LR003", dto.getId());
        assertEquals("STAFF003", dto.getStaffId());
        assertEquals(LocalDate.of(2025, 11, 5), dto.getStartDate());
        assertEquals(LocalDate.of(2025, 11, 6), dto.getEndDate());
        assertEquals(LeaveStatus.REJECTED, dto.getStatus());
    }

    @Test
    @DisplayName("toDTOList maps list of LeaveRequestEntity to list of LeaveRequestDto")
    void testToDTOList() {
        LeaveRequestEntity entity1 = new LeaveRequestEntity();
        entity1.setId("LR001");
        entity1.setStaffId("STAFF001");
        entity1.setStartDate(LocalDate.of(2025, 9, 20));
        entity1.setEndDate(LocalDate.of(2025, 9, 22));
        entity1.setStatus("APPROVED");

        LeaveRequestEntity entity2 = new LeaveRequestEntity();
        entity2.setId("LR002");
        entity2.setStaffId("STAFF002");
        entity2.setStartDate(LocalDate.of(2025, 10, 1));
        entity2.setEndDate(LocalDate.of(2025, 10, 3));
        entity2.setStatus("PENDING");

        List<LeaveRequestDto> dtos = LeaveRequestMapper.toDTOList(List.of(entity1, entity2));

        assertEquals(2, dtos.size());
        assertEquals("LR001", dtos.get(0).getId());
        assertEquals(LeaveStatus.APPROVED, dtos.get(0).getStatus());
        assertEquals("LR002", dtos.get(1).getId());
        assertEquals(LeaveStatus.PENDING, dtos.get(1).getStatus());
    }
}
