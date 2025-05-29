package com.example.kursach.db.entity.repository;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.db.entity.ClothSizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClothesSizesRepository extends JpaRepository<ClothSizes, Integer> {

}
