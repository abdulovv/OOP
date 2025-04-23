package com.example.kursach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @GetMapping("/")  // Обрабатывает запросы к корневому пути
    public String redirectToMainPage() {
        return "redirect:/mainPage";  // Перенаправление на /mainPage
    }

    @GetMapping("/mainPage")  // Ваша основная страница
    public String showMainPage() {
        return "mainPage";  // Возвращает шаблон mainPage.html
    }
}
