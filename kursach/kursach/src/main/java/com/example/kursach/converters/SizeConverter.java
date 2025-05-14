package com.example.kursach.converters;

import com.example.kursach.domain.Size;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SizeConverter implements AttributeConverter<Size, String> {
    @Override
    public String convertToDatabaseColumn(Size size) {
        String dbColumn;
        if (size == Size.XSMALL) {
            dbColumn = "XS";
        }else if (size == Size.SMALL) {
            dbColumn = "S";
        }else if (size == Size.MEDIUM) {
            dbColumn = "M";
        }else if (size == Size.LARGE) {
            dbColumn = "L";
        }else {
            dbColumn = "XL";
        }
        return dbColumn;
    }

    @Override
    public Size convertToEntityAttribute(String s) {
        Size dbAttribute;
        if (s.equals("XS")) {
            dbAttribute = Size.XSMALL;
        }else if (s.equals("S")) {
            dbAttribute = Size.SMALL;
        }else if (s.equals("M")) {
            dbAttribute = Size.MEDIUM;
        }else if (s.equals("L")) {
            dbAttribute = Size.LARGE;
        }else {
            dbAttribute = Size.XLARGE;
        }
        return dbAttribute;
    }
}
