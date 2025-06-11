// com/example/kursach/service/StatisticsServiceTest.java
package com.example.kursach.service;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.db.entity.ClothSize;
import com.example.kursach.db.entity.OrderItem;
import com.example.kursach.db.entity.repository.ClothRepository;
import com.example.kursach.db.entity.repository.ClothSizeRepository;
import com.example.kursach.db.entity.repository.OrderItemRepository;
import com.example.kursach.domain.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StatisticsServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ClothRepository clothRepository;

    @Mock
    private ClothSizeRepository clothSizeRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSalesByClothSize_returnsSalesCountBySize() {
        // Arrange
        OrderItem item1 = new OrderItem(1, 101, 201, 2);
        OrderItem item2 = new OrderItem(2, 101, 202, 3);
        OrderItem item3 = new OrderItem(3, 101, 201, 1);
        when(orderItemRepository.findByClothId(101)).thenReturn(Arrays.asList(item1, item2, item3));

        ClothSize size201 = new ClothSize();
        size201.setSize(Size.S);
        when(clothSizeRepository.findById(201)).thenReturn(Optional.of(size201));

        ClothSize size202 = new ClothSize();
        size202.setSize(Size.M);
        when(clothSizeRepository.findById(202)).thenReturn(Optional.of(size202));

        // Act
        Map<String, Integer> result = statisticsService.getSalesByClothSize(101);

        // Assert
        assertEquals(2, result.size());
        assertEquals(3, result.get("S"));
        assertEquals(3, result.get("M"));
    }

    @Test
    void getTopSellingOverall_returnsTopSellingClothes() {
        // Arrange
        OrderItem item1 = new OrderItem(1, 101, 201, 2);
        OrderItem item2 = new OrderItem(2, 102, 202, 3);
        OrderItem item3 = new OrderItem(3, 101, 201, 1);
        when(orderItemRepository.findAll()).thenReturn(Arrays.asList(item1, item2, item3));

        Cloth cloth1 = new Cloth();
        cloth1.setId(101);
        cloth1.setName("T-shirt");
        cloth1.setImage_url("/image/t-shirt.jpg");
        when(clothRepository.findById(101)).thenReturn(Optional.of(cloth1));

        Cloth cloth2 = new Cloth();
        cloth2.setId(102);
        cloth2.setName("Jeans");
        cloth2.setImage_url("/image/jeans.jpg");
        when(clothRepository.findById(102)).thenReturn(Optional.of(cloth2));

        // Act
        List<Map<String, Object>> result = statisticsService.getTopSellingOverall();

        // Assert
        assertEquals(2, result.size());
        assertEquals(101, result.get(0).get("clothId"));
        assertEquals("T-shirt", result.get(0).get("name"));
        assertEquals("/image/t-shirt.jpg", result.get(0).get("imageUrl"));
        assertEquals(102, result.get(1).get("clothId"));
        assertEquals("Jeans", result.get(1).get("name"));
        assertEquals("/image/jeans.jpg", result.get(1).get("imageUrl"));
    }

    @Test
    void getLeastSellingOverall_returnsLeastSellingClothes() {
        // Arrange
        when(orderItemRepository.findAll()).thenReturn(Collections.emptyList()); // No orders
        Cloth cloth1 = new Cloth();
        cloth1.setId(101);
        cloth1.setName("T-shirt");
        cloth1.setImage_url("/image/t-shirt.jpg");
        Cloth cloth2 = new Cloth();
        cloth2.setId(102);
        cloth2.setName("Jeans");
        cloth2.setImage_url("/image/jeans.jpg");

        when(clothRepository.findAll()).thenReturn(Arrays.asList(cloth1, cloth2));

        // Act
        List<Map<String, Object>> result = statisticsService.getLeastSellingOverall();

        // Assert
        assertEquals(2, result.size());
        assertEquals(101, result.get(0).get("clothId"));
        assertEquals("T-shirt", result.get(0).get("name"));
        assertEquals("/image/t-shirt.jpg", result.get(0).get("imageUrl"));
        assertEquals(102, result.get(1).get("clothId"));
        assertEquals("Jeans", result.get(1).get("name"));
        assertEquals("/image/jeans.jpg", result.get(1).get("imageUrl"));
    }
}