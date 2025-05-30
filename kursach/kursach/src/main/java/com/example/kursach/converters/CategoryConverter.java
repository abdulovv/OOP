package com.example.kursach.converters;

import com.example.kursach.domain.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(Category category) {
        String dbColumn;

        if (category == Category.Hat) {
            dbColumn = "Hat";
        } else if (category == Category.Slippers) {
            dbColumn = "Slippers";
        } else if (category == Category.Shorts) {
            dbColumn = "Shorts";
        } else if (category == Category.Shirt) {
            dbColumn = "Shirt";
        } else if (category == Category.Socks) {
            dbColumn = "Socks";
        } else if (category == Category.Jacket) {
            dbColumn = "Jacket";
        } else if (category == Category.Pants) {
            dbColumn = "Pants";
        } else if (category == Category.Sweatshirt) {
            dbColumn = "Sweatshirt";
        } else if (category == Category.Glasses) {
            dbColumn = "Glasses";
        } else {
            dbColumn = "Scarf";
        }

        return dbColumn;
    }

    @Override
    public Category convertToEntityAttribute(String dbData) {
        Category entityCategory;

        if (dbData.equals("Hat")) {
            entityCategory = Category.Hat;
        } else if (dbData.equals("Slippers")) {
            entityCategory = Category.Slippers;
        } else if (dbData.equals("Shorts")) {
            entityCategory = Category.Shorts;
        } else if (dbData.equals("Shirt")) {
            entityCategory = Category.Shirt;
        } else if (dbData.equals("Socks")) {
            entityCategory = Category.Socks;
        } else if (dbData.equals("Jacket")) {
            entityCategory = Category.Jacket;
        } else if (dbData.equals("Pants")) {
            entityCategory = Category.Pants;
        } else if (dbData.equals("Sweatshirt")) {
            entityCategory = Category.Sweatshirt;
        } else if (dbData.equals("Glasses")) {
            entityCategory = Category.Glasses;
        } else {
            entityCategory = Category.Scarf;
        }

        return entityCategory;
    }
}