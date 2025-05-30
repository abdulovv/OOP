package com.example.kursach.controller;

import com.example.kursach.db.entity.repository.ClothRepository;
import com.example.kursach.db.entity.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sdfldsk")
public class MainPageController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClothRepository clothRepository;





}