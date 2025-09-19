package com.example.enterpriselbs.domain.aggregates.factory;

import com.example.enterpriselbs.domain.Identity;

import java.util.UUID;

public final class UniqueIdFactory {
    private UniqueIdFactory() {}
    public static Identity createID() {
        String uniqueValue = UUID.randomUUID().toString();
        return new Identity(uniqueValue);
    }
}