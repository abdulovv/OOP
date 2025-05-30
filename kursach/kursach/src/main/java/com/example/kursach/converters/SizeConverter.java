package com.example.kursach.converters;

import com.example.kursach.domain.Size;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SizeConverter implements AttributeConverter<Size, String> {
    @Override
    public String convertToDatabaseColumn(Size size) {
        String dbColumn;
        if (size == Size.XS) {
            dbColumn = "XS";
        }else if (size == Size.S) {
            dbColumn = "S";
        }else if (size == Size.M) {
            dbColumn = "M";
        }else if (size == Size.L) {
            dbColumn = "L";
        }else {
            dbColumn = "XL";
        }
        return dbColumn;
    }

    @Override
    public Size convertToEntityAttribute(String s) {
        return switch (s) {
            case "XS" -> Size.XS;
            case "S" -> Size.S;
            case "M" -> Size.M;
            case "L" -> Size.L;
            default -> Size.XL;
        };
    }
}
