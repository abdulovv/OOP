package com.example.kursach.converters;

import com.example.kursach.domain.Sex;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class SexConverter implements AttributeConverter<Sex, String> {

    @Override
    public String convertToDatabaseColumn(Sex sex) {
        return Objects.equals(sex.toString(), "Male") ? "Male" : "Female";
    }

    @Override
    public Sex convertToEntityAttribute(String s) {
        return s.equals("Male") ? Sex.MALE : Sex.FEMALE;
    }
}
