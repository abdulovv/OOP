// com/example/kursach/db/entity/repository/ClothRepository.java
package com.example.kursach.db.entity.repository;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.domain.Sex;
import com.example.kursach.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothRepository extends JpaRepository<Cloth, Integer> {
    @Query(value = "SELECT * FROM clothes WHERE sex = :#{#sex.name()}", nativeQuery = true)
    List<Cloth> findBySex(Sex sex);

    @Query(value = "SELECT * FROM clothes WHERE type = :#{#category.name()}", nativeQuery = true)
    List<Cloth> findByCategory(Category category);
}