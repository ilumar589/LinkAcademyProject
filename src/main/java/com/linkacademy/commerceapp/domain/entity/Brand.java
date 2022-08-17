package com.linkacademy.commerceapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

public enum Brand {
    NARWHAL_PENS("Narwhal Pens"),
    KOKUYO("Kokuyo"),
    INTERNATIONAL_PAPER("International Paper"),
    WILDCRAFT_BACKPACK("Wildcraft Backpack"),
    MCGRAW_HILL_EDUCATION("McGraw-Hill Education"),
    UNKNOWN_BRAND("Unknown brand");

    private final String description;

    Brand(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static Brand from(String description) {
        if (StringUtils.isEmpty(description)) {
            throw new IllegalArgumentException("In Brand Enum: NULL or empty string");
        }

        for (Brand brand : values()) {
            if (brand.description.equals(description)) {
                return brand;
            }
        }

        throw new IllegalArgumentException("In Brand Enum: No brand found for description " + description);
    }
}
