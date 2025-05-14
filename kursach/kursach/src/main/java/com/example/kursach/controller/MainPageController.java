package com.example.kursach.controller;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.db.entity.Order;
import com.example.kursach.db.entity.repository.ClothRepository;
import com.example.kursach.db.entity.repository.OrderRepository;
import com.example.kursach.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClothRepository clothRepository;

    @GetMapping("/")
    public String redirectToMainPage() {
        return "redirect:/mainPage";
    }

    @GetMapping("/mainPage")
    public String showMainPage() {
        return "mainPage";
    }

    @GetMapping("/addCloth")
    public String showAddClothPage() {
        Cloth cloth = new Cloth("шорты", 59.99, Type.LegWear);
        clothRepository.save(cloth);

        Order order = new Order("+375(29)147-93-24", "Abdulov A. A.");
        orderRepository.save(order);

        return "redirect:/mainPage";
    }

    @GetMapping("/addOrder")
    public String showAddOrderPage() {
        Order order = new Order("+375(29)147-93-24", "Abdulov A. A.");
        orderRepository.save(order);

        return "redirect:/mainPage";
    }


}
