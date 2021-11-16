package com.mvc.project.enums;

import lombok.Getter;


@Getter
public enum RoleType {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");


    private final String name;

    RoleType(String name) {
        this.name = name;
    }
}
