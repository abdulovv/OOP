package com.example.kursach.controller;

import com.example.kursach.db.entity.Cloth;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class ClothController {
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/clothes")
    public List<Map<String, Object>> getProducts() {
        return Arrays.asList(
                Map.of("id", 1, "name", "Футболка", "price", 25.00, "image", "/images/tshirt.jpg"),
                Map.of("id", 2, "name", "Джинсы", "price", 75.00, "image", "/images/jeans.jpg"),
                Map.of("id", 3, "name", "Шорты", "price", 49.99, "image", "/images/jeans.jpg"),
                Map.of("id", 4, "name", "Носки", "price", 24.99, "image", "/images/jeans.jpg"),
                Map.of("id", 5, "name", "Очки", "price", 19.99, "image", "/images/jeans.jpg"),
                Map.of("id", 6, "name", "Перчатки", "price", 12.99, "image", "/images/jeans.jpg"),
                Map.of("id", 7, "name", "Шляпа", "price", 26.99, "image", "/images/jeans.jpg"),
                Map.of("id", 8, "name", "Куртка", "price", 299.00, "image", "/images/jeans.jpg"),
                Map.of("id", 9, "name", "Казино", "price", 99.00, "image", "/images/jeans.jpg")
        );
    }
}
