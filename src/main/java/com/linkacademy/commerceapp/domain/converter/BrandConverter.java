package com.linkacademy.commerceapp.domain.converter;

import com.linkacademy.commerceapp.domain.entity.Brand;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BrandConverter implements AttributeConverter<Brand, String> {
    @Override
    public String convertToDatabaseColumn(Brand brand) {
        return null;
    }

    @Override
    public Brand convertToEntityAttribute(String s) {
        return null;
    }
}
