package com.example.kursach.db.entity.repository;

import com.example.kursach.db.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByClothId(Integer clothId);
}
