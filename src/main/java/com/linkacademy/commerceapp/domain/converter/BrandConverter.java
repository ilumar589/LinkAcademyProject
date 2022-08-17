package com.linkacademy.commerceapp.domain.converter;

import com.linkacademy.commerceapp.domain.entity.Brand;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BrandConverter implements AttributeConverter<Brand, String> {
    @Override
    public String convertToDatabaseColumn(Brand brand) {
        return brand.getDescription();
    }

    @Override
    public Brand convertToEntityAttribute(String description) {
        return Brand.from(description);
    }
}
