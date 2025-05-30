package com.example.kursach.service;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.db.entity.ClothSize;
import com.example.kursach.db.entity.repository.ClothRepository;
import com.example.kursach.db.entity.repository.ClothSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClothService {
    private final ClothSizeRepository clothSizeRepository;
    private final ClothRepository clothRepository;

    @Autowired
    public ClothService(ClothRepository clothRepository, ClothSizeRepository clothSizeRepository) {
        this.clothRepository = clothRepository;
        this.clothSizeRepository = clothSizeRepository;
    }

    public List<Cloth> getAllClothes() {
        return clothRepository.findAll();
    }

    public Cloth getClothById(int id) {
        return clothRepository.findById(id).orElse(null);
    }

    @GetMapping("/{clothId}/sizes")
    public List<Object> getSizesForCloth(@PathVariable int clothId) {
        return clothSizeRepository.findClothSizesByCloth_Id(clothId)
                .stream()
                .map(clothSize -> {
                    return new Object() {
                        public String size = clothSize.getSize().name();
                        public Integer count = clothSize.getCount();
                    };
                })
                .collect(Collectors.toList());
    }

}