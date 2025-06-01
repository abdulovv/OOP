package com.example.kursach.service;

import com.example.kursach.db.entity.ClothSize;
import com.example.kursach.db.entity.Order;
import com.example.kursach.db.entity.OrderItem;
import com.example.kursach.db.entity.repository.ClothSizeRepository;
import com.example.kursach.db.entity.repository.OrderItemRepository;
import com.example.kursach.db.entity.repository.OrderRepository;
import com.example.kursach.domain.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ClothSizeRepository clothSizeRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ClothSizeRepository clothSizeRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.clothSizeRepository = clothSizeRepository;
    }

    public Order saveOrder(String fullName, String contactInfo, List<Map<String, Object>> cartItems) {
        Order order = new Order();
        order.setFullName(fullName);
        order.setContactInfo(contactInfo);
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        for (Map<String, Object> item : cartItems) {
            Integer clothId = Integer.parseInt(item.get("id").toString());
            String sizeStr = item.get("size").toString();
            Integer quantity = Integer.parseInt(item.get("quantity").toString());

            try {
                Size sizeEnum = Size.valueOf(sizeStr);
                Optional<ClothSize> clothSizeOptional = clothSizeRepository.findByClothIdAndSize(clothId, sizeEnum);

                if (clothSizeOptional.isPresent()) {
                    ClothSize clothSize = clothSizeOptional.get();
                    if (clothSize.getCount() >= quantity) {
                        clothSize.setCount(clothSize.getCount() - quantity);
                        clothSizeRepository.save(clothSize);

                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrderId(savedOrder.getId());
                        orderItem.setClothId(clothId);
                        orderItem.setSizeId(getSizeIdFromClothSize(clothSize));
                        orderItem.setCount(quantity);
                        orderItemRepository.save(orderItem);
                    } else {
                        throw new RuntimeException("Недостаточно товара на складе: " + item.get("name") + ", размер: " + sizeStr);
                    }
                } else {
                    throw new RuntimeException("Размер товара не найден: " + item.get("name") + ", размер: " + sizeStr);
                }
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Некорректный размер: " + sizeStr);
            }
        }

        return savedOrder;
    }

    private Integer getSizeIdFromClothSize(ClothSize clothSize) {
        Optional<ClothSize> foundClothSize = clothSizeRepository.findByClothIdAndSize(clothSize.getCloth().getId(), clothSize.getSize());
        return foundClothSize.map(ClothSize::getId).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}