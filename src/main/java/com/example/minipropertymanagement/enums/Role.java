package com.example.minipropertymanagement.enums;

public enum Role {
    ADMIN, USER, OWNER;

    public String getRole() {
        return this.name();
    }
}
