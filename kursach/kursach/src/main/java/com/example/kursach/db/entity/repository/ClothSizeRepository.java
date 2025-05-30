package com.example.kursach.db.entity.repository;

import com.example.kursach.db.entity.ClothSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClothSizeRepository extends JpaRepository<ClothSize, Integer> {
    List<ClothSize> findClothSizesByCloth_Id(Integer clothId);

}
