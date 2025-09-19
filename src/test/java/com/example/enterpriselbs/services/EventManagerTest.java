//package com.example.enterpriselbs.services;
//
//import com.example.enterpriselbs.application.services.EventStoreService;
//import com.example.enterpriselbs.application.services.LocalDomainEventManager;
//import com.example.enterpriselbs.domain.Identity;
//import com.example.enterpriselbs.domain.aggregates.LeaveRequestAggregate;
//import com.example.enterpriselbs.domain.aggregates.factory.LeaveRequestFactoryWithEvent;
//import com.example.enterpriselbs.domain.valueObjects.LeavePeriod;
//import com.example.enterpriselbs.infrastructure.repositories.EventStoreRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.time.LocalDate;
//
//public class EventManagerTest {
//    @Test
//    void testManageEvent() {
//        EventStoreRepository mockRepo = Mockito.mock(EventStoreRepository.class);
//
//        EventStoreService service = new EventStoreService(mockRepo);
//
//        LocalDomainEventManager manager = new LocalDomainEventManager(service);
//
//        LeaveRequestFactoryWithEvent factory = new LeaveRequestFactoryWithEvent();
//        LeaveRequestAggregate agg = factory.create(
//                new Identity("staff-1"),
//                new LeavePeriod(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5))
//        );
//
//        manager.manageDomainEvents(this, agg.listOfDomainEvents());
//
//        Mockito.verify(mockRepo, Mockito.times(1)).save(Mockito.any());
//    }
//}
