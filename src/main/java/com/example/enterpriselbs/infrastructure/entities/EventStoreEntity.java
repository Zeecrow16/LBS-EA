package com.example.enterpriselbs.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "event_store")
@Getter
@ToString
public class EventStoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate occurredOn;
    private String eventType;

    @Column(length = 2000)
    private String payload;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getOccurredOn() { return occurredOn; }
    public void setOccurredOn(LocalDate occurredOn) { this.occurredOn = occurredOn; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    @Override
    public String toString() {
        return "EventStoreEntity{" +
                "id=" + id +
                ", occurredOn=" + occurredOn +
                ", eventType='" + eventType + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}