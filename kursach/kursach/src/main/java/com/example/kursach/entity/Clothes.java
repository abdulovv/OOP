package com.example.kursach.entity;

import lombok.Data;

@Data
public abstract class Clothes {
    protected String name;
    protected double price;
    protected double size;
    protected byte count;
}
