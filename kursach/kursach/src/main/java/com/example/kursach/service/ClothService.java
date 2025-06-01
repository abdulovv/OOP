// com/example/kursach/service/ClothService.java
package com.example.kursach.service;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.db.entity.ClothSize;
import com.example.kursach.db.entity.repository.ClothRepository;
import com.example.kursach.db.entity.repository.ClothSizeRepository;
import com.example.kursach.domain.Category;
import com.example.kursach.domain.Sex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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

    @Transactional(readOnly = true)
    public List<Cloth> getAllClothes(String gender, String category, String size, String sort, String order, String query) {
        List<Cloth> clothes = clothRepository.findAll();

        if (gender != null && !gender.isEmpty()) {
            try {
                Sex sex = Sex.valueOf(gender.toUpperCase());
                clothes = clothes.stream()
                        .filter(cloth -> cloth.getSex() == sex)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка при преобразовании пола: " + gender);
            }
        }

        if (category != null && !category.isEmpty()) {
            try {
                Category cat = Category.valueOf(category);
                clothes = clothes.stream()
                        .filter(cloth -> cloth.getCategory() == cat)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка при преобразовании категории: " + category);
            }
        }

        if (size != null && !size.isEmpty()) {
            clothes = clothes.stream()
                    .filter(cloth -> {
                        List<ClothSize> sizes = clothSizeRepository.findClothSizesByCloth_Id(cloth.getId());
                        return sizes.stream().anyMatch(clothSize -> clothSize.getSize().name().equalsIgnoreCase(size) && clothSize.getCount() > 0);
                    })
                    .collect(Collectors.toList());
        }

        if (sort != null && sort.equals("price")) {
            if (order != null && order.equalsIgnoreCase("asc")) {
                clothes.sort(Comparator.comparing(Cloth::getPrice));
            } else if (order != null && order.equalsIgnoreCase("desc")) {
                clothes.sort(Comparator.comparing(Cloth::getPrice).reversed());
            }
        }

        if (query != null && !query.isEmpty()) {
            String lowerCaseQuery = query.toLowerCase();
            clothes = clothes.stream()
                    .filter(cloth -> (
                            cloth.getName() != null &&
                            cloth.getName().toLowerCase().contains(lowerCaseQuery))
                    )
                    .collect(Collectors.toList());
        }//поправка была

        return clothes;
    }

    private List<Cloth> getClothesByCategory(String category) {
        try {
            Category cat = Category.valueOf(category.toUpperCase());
            return clothRepository.findByCategory(cat);
        } catch (IllegalArgumentException e) {
            return clothRepository.findAll();
        }
    }

    public Cloth getClothById(int id) {
        return clothRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Object> getSizesForCloth(int clothId) {
        return clothSizeRepository.findClothSizesByCloth_Id(clothId)
                .stream()
                .map(clothSize -> new Object() {
                    public String size = clothSize.getSize().name();
                    public Integer count = clothSize.getCount();
                })
                .collect(Collectors.toList());
    }
}