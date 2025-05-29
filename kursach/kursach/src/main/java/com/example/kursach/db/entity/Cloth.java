package com.example.kursach.db.entity;

import com.example.kursach.converters.CategoryConverter;
import com.example.kursach.converters.SexConverter;
import com.example.kursach.domain.Sex;
import com.example.kursach.domain.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "clothes")
public class Cloth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "type")
    @Convert(converter = CategoryConverter.class)
    private Category category;
    @Column(name = "sex")
    @Convert(converter = SexConverter.class)
    private Sex sex;

    public Cloth(String name, double price, Category category, Sex sex) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.sex = sex;
    }

    public Cloth() {

    }

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClothSizes> sizes;
}
