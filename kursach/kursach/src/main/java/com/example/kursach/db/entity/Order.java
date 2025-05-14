package com.example.kursach.db.entity;

import com.example.kursach.converters.DateTimeConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Convert(converter = DateTimeConverter.class)
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "contact_info")
    private String contactInfo;
    @Column(name = "full_name")
    private String fullName;

    public Order(String contactInfo, String fullName) {
        this.orderDate = LocalDateTime.now();
        this.contactInfo = contactInfo;
        this.fullName = fullName;
    }

    public Order() {

    }
}
