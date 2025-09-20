package com.example.enterpriselbs.mappers;

import com.example.enterpriselbs.application.dto.StaffDto;
import com.example.enterpriselbs.domain.Identity;
import com.example.enterpriselbs.domain.aggregates.StaffAggregate;
import com.example.enterpriselbs.domain.valueObjects.FullName;
import com.example.enterpriselbs.domain.valueObjects.Password;
import com.example.enterpriselbs.domain.valueObjects.Role;
import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import com.example.enterpriselbs.infrastructure.mappers.StaffMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaffMapperTest {
    @Test
    @DisplayName("toDomain maps StaffEntity to StaffAggregate correctly")
    public void testToDomain() {
        StaffEntity entity = new StaffEntity();
        entity.setId("STAFF001");
        entity.setUsername("jjoyce");
        entity.setFirstName("James");
        entity.setSurname("Joyce");
        entity.setRole("STAFF");
        entity.setManagerId("MGR001");
        entity.setDepartmentId("DEP001");
        entity.setLeaveAllocation(15);
        entity.setPasswordHash("ulysses2000");

        StaffAggregate aggregate = StaffMapper.toDomain(entity);

        assertEquals("STAFF001", aggregate.id().value());
        assertEquals("jjoyce", aggregate.username());
        assertEquals("James", aggregate.fullName().firstName());
        assertEquals("Joyce", aggregate.fullName().surname());
        assertEquals(Role.STAFF, aggregate.role());
        assertEquals("MGR001", aggregate.managerId().value());
        assertEquals("DEP001", aggregate.departmentId().value());
        assertEquals(15, aggregate.leaveAllocation());
        assertEquals("ulysses2000", aggregate.password().value());
    }

    @Test
    @DisplayName("toJpa maps StaffAggregate to StaffEntity correctly")
    public void testToJpa() {
        StaffAggregate aggregate = StaffAggregate.createWithEvent(
                new Identity("STAFF002"),
                "r.dahl",
                new FullName("Roald", "Dahl"),
                Role.MANAGER,
                new Identity("MGR002"),
                new Identity("DEP002"),
                20,
                new Password("pleasesircanIhavesomemore")
        );

        StaffEntity entity = StaffMapper.toJpa(aggregate);

        assertEquals("STAFF002", entity.getId());
        assertEquals("r.dahl", entity.getUsername());
        assertEquals("Roald", entity.getFirstName());
        assertEquals("Dahl", entity.getSurname());
        assertEquals("MANAGER", entity.getRole());
        assertEquals("MGR002", entity.getManagerId());
        assertEquals("DEP002", entity.getDepartmentId());
        assertEquals(20, entity.getLeaveAllocation());
        assertEquals("pleasesircanIhavesomemore", entity.getPasswordHash());
    }

    @Test
    @DisplayName("toDTO maps StaffEntity to StaffDto correctly")
    public void testToDTO() {
        StaffEntity entity = new StaffEntity();
        entity.setId("STAFF003");
        entity.setUsername("bwayne");
        entity.setFirstName("Bruce");
        entity.setSurname("Wayne");
        entity.setRole("ADMIN");
        entity.setManagerId("MGR003");
        entity.setDepartmentId("DEP003");
        entity.setLeaveAllocation(25);

        StaffDto dto = StaffMapper.toDTO(entity);

        assertEquals("STAFF003", dto.getId());
        assertEquals("bwayne", dto.getUsername());
        assertEquals("Bruce", dto.getFirstName());
        assertEquals("Wayne", dto.getSurname());
        assertEquals(Role.ADMIN, dto.getRole());
        assertEquals("MGR003", dto.getManagerId());
        assertEquals("DEP003", dto.getDepartmentId());
        assertEquals(25, dto.getLeaveAllocation());
    }

    @Test
    @DisplayName("toDTOList maps list of StaffEntity to list of StaffDto")
    public void testToDTOList() {
        StaffEntity entity1 = new StaffEntity();
        entity1.setId("STAFF001");
        entity1.setUsername("shelley.m");
        entity1.setFirstName("Mary");
        entity1.setSurname("Shelley");
        entity1.setRole("STAFF");
        entity1.setLeaveAllocation(10);

        StaffEntity entity2 = new StaffEntity();
        entity2.setId("STAFF002");
        entity2.setUsername("a.huxley");
        entity2.setFirstName("Aldous");
        entity2.setSurname("Huxley");
        entity2.setRole("MANAGER");
        entity2.setLeaveAllocation(20);

        List<StaffDto> dtos = StaffMapper.toDTOList(List.of(entity1, entity2));

        assertEquals(2, dtos.size());
        assertEquals("STAFF001", dtos.get(0).getId());
        assertEquals(Role.STAFF, dtos.get(0).getRole());
        assertEquals("STAFF002", dtos.get(1).getId());
        assertEquals(Role.MANAGER, dtos.get(1).getRole());
    }
}
