package com.example.kursach.service;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.db.entity.ClothSize;
import com.example.kursach.db.entity.OrderItem;
import com.example.kursach.db.entity.repository.ClothRepository;
import com.example.kursach.db.entity.repository.ClothSizeRepository;
import com.example.kursach.db.entity.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final OrderItemRepository orderItemRepository;
    private final ClothRepository clothRepository;
    private final ClothSizeRepository clothSizeRepository;

    @Autowired
    public StatisticsService(OrderItemRepository orderItemRepository, ClothRepository clothRepository, ClothSizeRepository clothSizeRepository) {
        this.orderItemRepository = orderItemRepository;
        this.clothRepository = clothRepository;
        this.clothSizeRepository = clothSizeRepository;
    }

    public Map<String, Integer> getSalesByClothSize(Integer clothId) {
        List<OrderItem> orderItems = orderItemRepository.findByClothId(clothId);
        Map<String, Integer> salesBySize = new HashMap<>();

        for (OrderItem item : orderItems) {
            ClothSize clothSize = clothSizeRepository.findById(item.getSizeId()).orElse(null);
            if (clothSize != null) {
                String size = clothSize.getSize().name();
                int count = item.getCount();
                salesBySize.put(size, salesBySize.getOrDefault(size, 0) + count);
            }
        }
        return salesBySize;
    }

    public List<Map<String, Object>> getTopSellingOverall() {
        List<OrderItem> allOrderItems = orderItemRepository.findAll();
        Map<Integer, Integer> clothSalesCount = new HashMap<>();

        for (OrderItem item : allOrderItems) {
            clothSalesCount.put(item.getClothId(), clothSalesCount.getOrDefault(item.getClothId(), 0) + item.getCount());
        }

        return clothSalesCount.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> {
                    Cloth cloth = clothRepository.findById(entry.getKey()).orElse(null);
                    if (cloth != null) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("clothId", cloth.getId());
                        item.put("name", cloth.getName());
                        item.put("imageUrl", cloth.getImage_url());
                        item.put("totalSales", entry.getValue());
                        return item;
                    }
                    return null;
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getLeastSellingOverall() {
        List<Cloth> allClothes = clothRepository.findAll();
        List<OrderItem> allOrderItems = orderItemRepository.findAll();

        List<Integer> soldClothIds = allOrderItems.stream()
                .map(OrderItem::getClothId)
                .distinct()
                .toList();

        return allClothes.stream()
                .filter(cloth -> !soldClothIds.contains(cloth.getId()))
                .limit(5)
                .map(cloth -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("clothId", cloth.getId());
                    item.put("name", cloth.getName());
                    item.put("imageUrl", cloth.getImage_url());
                    return item;
                })
                .collect(Collectors.toList());
    }
}