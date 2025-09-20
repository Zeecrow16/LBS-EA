package com.example.enterpriselbs.infrastructure.repositories;

import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<StaffEntity, String> {
    @Query("FROM StaffEntity u WHERE u.username=:username")
    Optional<StaffEntity> findByUsername(String username);
}