package com.example.minipropertymanagement.domain.enums;

public enum Role {
    ADMIN, USER, OWNER;

    public String getRole() {
        return this.name();
    }
}
