package com.example.kursach.db.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "order_items")
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "cloth_id")
    private Integer clothId;
    @Column(name = "size_id")
    private Integer sizeId;
    @Column(name = "count")
    private Integer count;

    public OrderItem(Integer orderId, Integer clothId, Integer sizeId, Integer count) {
        this.orderId = orderId;
        this.clothId = clothId;
        this.sizeId = sizeId;
        this.count = count;
    }

    public OrderItem() {

    }
}
