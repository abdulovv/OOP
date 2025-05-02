package com.example.kursach.converters;

import com.example.kursach.domain.Type;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type, String> {
    @Override
    public String convertToDatabaseColumn(Type type) {
        String dbColumn;

        if (type == Type.LegWear) {
            dbColumn = "LegWear";
        }else if (type == Type.TopWear) {
            dbColumn = "TopWear";
        }else if (type == Type.FootWear) {
            dbColumn = "FootWear";
        }else if (type == Type.HeadWear) {
            dbColumn = "HeadWear";
        }else {
            dbColumn = "Unknown";
        }

        return dbColumn;
    }

    @Override
    public Type convertToEntityAttribute(String dbData) {
        Type entityType;

        if (dbData.equals("LegWear")) {
            entityType = Type.LegWear;
        }else if (dbData.equals("TopWear")) {
            entityType = Type.TopWear;
        }else if (dbData.equals("FootWear")) {
            entityType = Type.FootWear;
        }else if (dbData.equals("HeadWear")) {
            entityType = Type.HeadWear;
        }else {
            entityType = Type.Unknown;
        }

        return entityType;
    }
}