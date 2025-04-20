package ru.monitor.sensor.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("admin"),
    VIEWER("user");

    private final String username;

    Role(String username) {
        this.username = username;
    }
}
