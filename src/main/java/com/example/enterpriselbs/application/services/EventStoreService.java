package com.example.enterpriselbs.application.services;

import com.example.enterpriselbs.domain.events.LocalEvent;
import com.example.enterpriselbs.infrastructure.entities.EventStoreEntity;
import com.example.enterpriselbs.infrastructure.repositories.EventStoreRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
@AllArgsConstructor
public class EventStoreService {
    private final EventStoreRepository repository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public void append(LocalEvent event) {
        EventStoreEntity newEvent = new EventStoreEntity();
        newEvent.setOccurredOn(LocalDate.now());
        newEvent.setEventType(event.getClass().getSimpleName());
        newEvent.setPayload(event.toString());

        repository.save(newEvent);
        LOG.info("Added to event store {}", newEvent);
    }
}

//@Component
//public class EventStoreService {
//    private final EventStoreRepository eventRepo;
//    private final Logger LOG = LoggerFactory.getLogger(getClass());
//    public EventStoreService(EventStoreRepository eventRepo) {
//        this.eventRepo = eventRepo;
//    }
//    public void append(LocalEvent event) {
//        EventStoreEntity e = new EventStoreEntity();
//        e.setOccurredOn(LocalDate.now());
//        e.setEventType(event.getClass().getSimpleName());
//        e.setPayload(event.toString());
//
//        eventRepo.save(e);
//        LOG.info("Added event to store: {}", e);
//    }
//}