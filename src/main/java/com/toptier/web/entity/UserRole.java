package com.toptier.web.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }
    private final String value;
}
