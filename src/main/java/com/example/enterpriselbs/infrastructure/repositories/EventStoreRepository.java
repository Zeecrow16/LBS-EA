package com.example.enterpriselbs.infrastructure.repositories;

import com.example.enterpriselbs.infrastructure.entities.EventStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventStoreRepository extends JpaRepository<EventStoreEntity, Long> {
}
