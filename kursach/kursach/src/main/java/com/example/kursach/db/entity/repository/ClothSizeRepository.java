package com.example.kursach.db.entity.repository;

import com.example.kursach.db.entity.ClothSize;
import com.example.kursach.domain.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClothSizeRepository extends JpaRepository<ClothSize, Integer> {
    List<ClothSize> findClothSizesByCloth_Id(Integer clothId);

    Optional<ClothSize> findByClothIdAndSize(Integer clothId, Size size);
}
