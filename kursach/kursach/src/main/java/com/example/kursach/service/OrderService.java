package com.example.kursach.service;

import com.example.kursach.db.entity.*;
import com.example.kursach.db.entity.repository.ClothRepository; // Import ClothRepository
import com.example.kursach.db.entity.repository.ClothSizeRepository;
import com.example.kursach.db.entity.repository.OrderItemRepository;
import com.example.kursach.db.entity.repository.OrderRepository;
import com.example.kursach.domain.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ClothSizeRepository clothSizeRepository;
    private final ClothRepository clothRepository; // Add ClothRepository

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        ClothSizeRepository clothSizeRepository, ClothRepository clothRepository) { // Inject ClothRepository
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.clothSizeRepository = clothSizeRepository;
        this.clothRepository = clothRepository;
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<OrderItemDTO> getOrderItemsByOrderId(Integer orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private OrderItemDTO convertToDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setCount(orderItem.getCount());

        Cloth cloth = clothRepository.findById(orderItem.getClothId()).orElse(null);
        if (cloth != null) {
            dto.setClothName(cloth.getName());
            dto.setClothImageUrl(cloth.getImage_url());
        }

        ClothSize clothSize = clothSizeRepository.findById(orderItem.getSizeId()).orElse(null);
        if (clothSize != null) {
            dto.setSize(clothSize.getSize().toString());
        }
        return dto;
    }

    public static class OrderItemDTO {
        private String clothName;
        private String clothImageUrl;
        private String size;
        private Integer count;

        public String getClothName() {
            return clothName;
        }

        public void setClothName(String clothName) {
            this.clothName = clothName;
        }

        public String getClothImageUrl() {
            return clothImageUrl;
        }

        public void setClothImageUrl(String clothImageUrl) {
            this.clothImageUrl = clothImageUrl;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}