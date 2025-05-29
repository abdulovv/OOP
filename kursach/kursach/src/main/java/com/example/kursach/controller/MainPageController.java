package com.example.kursach.controller;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.db.entity.Order;
import com.example.kursach.db.entity.repository.ClothRepository;
import com.example.kursach.db.entity.repository.OrderRepository;
import com.example.kursach.domain.FilterRequest;
import com.example.kursach.domain.Sex;
import com.example.kursach.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sdfldsk")
public class MainPageController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClothRepository clothRepository;

    @GetMapping
    public List<Cloth> getAllClothes() {
        return clothRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cloth getClothById(@PathVariable Integer id) {
        return clothRepository.findById(id).get();
    }

    // Фильтрация товаров
    @PostMapping("/filter")
//    public List<Cloth> filterClothes(@RequestBody FilterRequest request) {
//        return clothRepository.filterByCriteria(
//                request.getSearchQuery(),
//                request.getSex(),
//                request.getCategory(),
//                request.getSizes(),
//                request.getPriceFrom(),
//                request.getPriceTo()
//        );
//    }


    @GetMapping("/")
    public String redirectToMainPage() {
        return "redirect:http://localhost:3000";
    }

    @GetMapping("/mainPage")
    public String showMainPage() {
        return "mainPage";
    }

    @GetMapping("/addCloth")
    public String showAddClothPage() {
        Cloth cloth = new Cloth("шорты", 59.99, Category.LegWear, Sex.MALE);
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