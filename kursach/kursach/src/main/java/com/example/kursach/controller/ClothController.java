// com/example/kursach/controller/ClothController.java
package com.example.kursach.controller;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.service.ClothService;
import jakarta.xml.bind.JAXBException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/clothes")
public class ClothController {
    private final ClothService clothService;

    public ClothController(ClothService clothService) {
        this.clothService = clothService;
    }

    @GetMapping("/")
    public List<Cloth> getAllClothes(@RequestParam(value = "gender", required = false) String gender,
                                     @RequestParam(value = "category", required = false) String category,
                                     @RequestParam(value = "size", required = false) String size,
                                     @RequestParam(value = "sort", required = false) String sort,
                                     @RequestParam(value = "order", required = false) String order,
                                     @RequestParam(value = "query", required = false) String query) throws IOException {
        return clothService.getAllClothes(gender, category, size, sort, order, query);
    }

    @GetMapping("/{id}")
    public Cloth getClothById(@PathVariable Integer id) {
        return clothService.getClothById(id);
    }

    @GetMapping("/{clothId}/sizes")
    public List<Object> getSizesForCloth(@PathVariable int clothId) {
        return clothService.getSizesForCloth(clothId);
    }
}