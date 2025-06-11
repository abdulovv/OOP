// com/example/kursach/service/OrderServiceTest.java
package com.example.kursach.service;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.db.entity.ClothSize;
import com.example.kursach.db.entity.Order;
import com.example.kursach.db.entity.OrderItem;
import com.example.kursach.db.entity.repository.ClothRepository;
import com.example.kursach.db.entity.repository.ClothSizeRepository;
import com.example.kursach.db.entity.repository.OrderItemRepository;
import com.example.kursach.db.entity.repository.OrderRepository;
import com.example.kursach.domain.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ClothSizeRepository clothSizeRepository;

    @Mock
    private ClothRepository clothRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders_returnsAllOrders() {
        // Arrange
        Order order1 = new Order("test1@example.com", "Test User 1");
        Order order2 = new Order("test2@example.com", "Test User 2");
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Act
        List<Order> result = orderService.getAllOrders();

        // Assert
        assertEquals(2, result.size());
        assertEquals(order1.getContactInfo(), result.get(0).getContactInfo());
        assertEquals(order2.getContactInfo(), result.get(1).getContactInfo());
    }

    @Test
    void getOrderItemsByOrderId_returnsOrderItemsWithDetails() {
        // Arrange
        OrderItem orderItem1 = new OrderItem(1, 10, 101, 2);
        OrderItem orderItem2 = new OrderItem(1, 20, 102, 1);
        when(orderItemRepository.findByOrderId(1)).thenReturn(Arrays.asList(orderItem1, orderItem2));

        Cloth cloth1 = new Cloth();
        cloth1.setName("T-shirt");
        cloth1.setImage_url("/image/t-shirt.jpg");
        when(clothRepository.findById(10)).thenReturn(Optional.of(cloth1));

        Cloth cloth2 = new Cloth();
        cloth2.setName("Jeans");
        cloth2.setImage_url("/image/jeans.jpg");
        when(clothRepository.findById(20)).thenReturn(Optional.of(cloth2));

        ClothSize clothSize1 = new ClothSize();
        clothSize1.setSize(Size.M);
        when(clothSizeRepository.findById(101)).thenReturn(Optional.of(clothSize1));

        ClothSize clothSize2 = new ClothSize();
        clothSize2.setSize(Size.L);
        when(clothSizeRepository.findById(102)).thenReturn(Optional.of(clothSize2));

        // Act
        List<OrderService.OrderItemDTO> result = orderService.getOrderItemsByOrderId(1);

        // Assert
        assertEquals(2, result.size());
        assertEquals("T-shirt", result.get(0).getClothName());
        assertEquals("/image/t-shirt.jpg", result.get(0).getClothImageUrl());
        assertEquals("M", result.get(0).getSize());
        assertEquals(2, result.get(0).getCount());

        assertEquals("Jeans", result.get(1).getClothName());
        assertEquals("/image/jeans.jpg", result.get(1).getClothImageUrl());
        assertEquals("L", result.get(1).getSize());
        assertEquals(1, result.get(1).getCount());
    }
}