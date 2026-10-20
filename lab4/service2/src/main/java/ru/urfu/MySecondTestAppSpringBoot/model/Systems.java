package ru.urfu.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Systems {
    ERP("ERP", "Enterprise Resource Planning"),
    CRM("CRM", "Customer Relationship Management"),
    WMS("WMS", "Warehouse Management System"),
    SERVICE_1("Service 1", "Сервис 1 (пересылка request в Сервис 2)");

    private final String name;
    private final String description;

    Systems(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static Systems fromName(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (Systems system : values()) {
            if (system.name.equalsIgnoreCase(value) || system.name().equalsIgnoreCase(value)) {
                return system;
            }
        }
        throw new IllegalArgumentException("Неизвестная система: " + value);
    }

    @Override
    public String toString() {
        return name;
    }
}
