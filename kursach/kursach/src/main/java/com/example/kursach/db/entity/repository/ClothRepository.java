package com.example.kursach.db.entity.repository;

import com.example.kursach.db.entity.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClothRepository extends JpaRepository<Cloth, Integer> {


}
