package com.example.kursach.controller;

import com.example.kursach.db.entity.Order;
import com.example.kursach.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Map<String, Object> payload) {
        try {
            String fullName = ((Map<String, String>) payload.get("customerInfo")).get("full_name");
            String contactInfo = ((Map<String, String>) payload.get("customerInfo")).get("contact_info");
            List<Map<String, Object>> cartItems = (List<Map<String, Object>>) payload.get("items");
            Order savedOrder = orderService.saveOrder(fullName, contactInfo, cartItems);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Ошибка при обработке заказа: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders(); // Вам нужно создать этот метод в OrderService
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}