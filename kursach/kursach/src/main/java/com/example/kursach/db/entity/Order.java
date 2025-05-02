package com.example.kursach.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;

import java.time.LocalDateTime;


@Data
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "order_date")
    private String orderDate;
    @Column(name = "contact_info")
    private String contactInfo;
    @Column(name = "full_name")
    private String fullName;


}
