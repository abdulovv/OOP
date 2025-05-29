package com.example.kursach.db.entity.repository;

import com.example.kursach.db.entity.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothRepository extends JpaRepository<Cloth, Integer> {
    List<Cloth> findByCategory(String category);

    // Метод для фильтрации товаров
    @Query("SELECT c FROM Cloth c WHERE " +
            "(:searchQuery IS NULL OR LOWER(c.name) LIKE CONCAT('%', LOWER(:searchQuery), '%')) " +
            "AND (:sex IS NULL OR c.sex = :sex) " +
            "AND (:category IS NULL OR c.category = :category) " +
            "AND (:size IS EMPTY OR c.size IN :sizes) " +
            "AND (:priceFrom IS NULL OR c.price >= :priceFrom) " +
            "AND (:priceTo IS NULL OR c.price <= :priceTo)")
    List<Cloth> filterByCriteria(
            @Param("searchQuery") String searchQuery,
            @Param("sex") String sex,
            @Param("category") String category,
            @Param("sizes") List<String> sizes,
            @Param("priceFrom") Double priceFrom,
            @Param("priceTo") Double priceTo
    );
}
