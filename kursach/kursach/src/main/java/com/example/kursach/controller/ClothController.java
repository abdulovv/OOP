package com.example.kursach.controller;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.service.ClothService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clothes")
public class ClothController {
    private final ClothService clothService;

    public ClothController(ClothService clothService) {
        this.clothService = clothService;
    }

    @GetMapping("/")
    public List<Cloth> getAllClothes() {
        return clothService.getAllClothes();
    }

    @GetMapping("/{id}")
    public Cloth getClothById(@PathVariable Integer id){
        return clothService.getClothById(id);
    }

    @GetMapping("/{clothId}/sizes")
    public List<Object> getSizesForCloth(@PathVariable int clothId) {
        return clothService.getSizesForCloth(clothId);
    }
}