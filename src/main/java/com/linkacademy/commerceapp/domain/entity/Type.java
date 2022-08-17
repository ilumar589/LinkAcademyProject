package com.linkacademy.commerceapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

public enum Type {
    WRITING("Writing"),
    READING("Reading"),
    CARRY("Carry");

    private final String description;

    Type(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static Type from(String description) {
        if (StringUtils.isEmpty(description)) {
            throw new IllegalArgumentException("In Type Enum: NULL or empty string");
        }

        for (Type type : values()) {
            if (type.description.equals(description)) {
                return type;
            }
        }

        throw new IllegalArgumentException("In Type Enum: No type found for description " + description);
    }
}
