package com.example.kursach.domain;

import lombok.Data;

import java.util.List;

@Data
public class FilterRequest {
    private String searchQuery; // Поиск по названию
    private String sex;         // Пол
    private String category;    // Категория
    private List<String> sizes; // Размеры
    private Double priceFrom;   // Цена от
    private Double priceTo;     // Цена до
}