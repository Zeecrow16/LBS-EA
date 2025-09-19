//package com.example.enterpriselbs.infrastructure.mappers;
//
//import com.example.enterpriselbs.domain.Identity;
//import com.example.enterpriselbs.domain.aggregates.DepartmentAggregate;
//import com.example.enterpriselbs.domain.valueObjects.DepartmentName;
//import com.example.enterpriselbs.infrastructure.entities.Department;
//
//public class DepartmentMapper {
//
//    public static DepartmentAggregate toAggregate(Department entity) {
//        return new DepartmentAggregate(
//                new Identity(entity.getId()),
//                new DepartmentName(entity.getName())
//        );
//    }
//
//    public static Department toEntity(DepartmentAggregate aggregate) {
//        Department entity = new Department();
//        entity.setId(aggregate.id().value());
//        entity.setName(aggregate.name().value());
//        return entity;
//    }
//}