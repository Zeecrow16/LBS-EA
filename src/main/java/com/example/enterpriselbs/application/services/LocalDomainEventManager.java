package com.example.enterpriselbs.application.services;

import com.example.enterpriselbs.domain.events.LocalEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocalDomainEventManager {

    private final EventStoreService eventStoreService;

    public LocalDomainEventManager(EventStoreService eventStoreService) {
        this.eventStoreService = eventStoreService;
    }

    public void manageDomainEvents(Object source, List<LocalEvent> events) {
        for (LocalEvent event : events) {
            eventStoreService.append(event);

        }
    }
}