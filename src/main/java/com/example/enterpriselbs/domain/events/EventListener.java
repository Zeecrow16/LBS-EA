package com.example.enterpriselbs.domain.events;

import com.example.enterpriselbs.application.services.LocalDomainEventManager;
import com.example.enterpriselbs.infrastructure.repositories.LeaveRequestRepository;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class EventListener {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleLocalEvent(LocalEvent event) {
        LOG.info("Event processed: {}", event.getClass().getSimpleName());
    }

}
