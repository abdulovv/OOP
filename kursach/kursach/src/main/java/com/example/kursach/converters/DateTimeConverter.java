package com.example.kursach.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class DateTimeConverter implements AttributeConverter<LocalDateTime, String> {
    @Override
    public String convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime.toString().substring(0, 19);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String localDateTime) {
        return LocalDateTime.parse(localDateTime);
    }
}
