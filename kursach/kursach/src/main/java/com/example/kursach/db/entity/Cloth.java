package com.example.kursach.db.entity;

import com.example.kursach.converters.TypeConverter;
import com.example.kursach.domain.Type;
import jakarta.persistence.*;
import lombok.Data;

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
    //@Convert(converter = TypeConverter.class)
    @Enumerated(EnumType.STRING)
    private Type type;

    public Cloth(String name, double price, Type type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Cloth() {

    }
}
