package com.example.kursach.converters;

import com.example.kursach.domain.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {
    @Override
    public String convertToDatabaseColumn(Category category) {
        String dbColumn;

        if (category == Category.LegWear) {
            dbColumn = "LegWear";
        }else if (category == Category.TopWear) {
            dbColumn = "TopWear";
        }else if (category == Category.FootWear) {
            dbColumn = "FootWear";
        }else if (category == Category.HeadWear) {
            dbColumn = "HeadWear";
        }else {
            dbColumn = "Unknown";
        }

        return dbColumn;
    }

    @Override
    public Category convertToEntityAttribute(String dbData) {
        Category entityCategory;

        if (dbData.equals("LegWear")) {
            entityCategory = Category.LegWear;
        }else if (dbData.equals("TopWear")) {
            entityCategory = Category.TopWear;
        }else if (dbData.equals("FootWear")) {
            entityCategory = Category.FootWear;
        }else if (dbData.equals("HeadWear")) {
            entityCategory = Category.HeadWear;
        }else {
            entityCategory = Category.Unknown;
        }

        return entityCategory;
    }
}