package com.example.kursach.db.entity;

import jakarta.persistence.*;
import com.example.kursach.domain.Size;
import lombok.Data;

@Data
@Table(name = "clothes_sizes")
@Entity
public class ClothSizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cloth_id", referencedColumnName = "id")
    private Cloth cloth;
    @Column(name = "size")
    private Size size;
    @Column(name = "stock_count")
    private Integer count;

    public ClothSizes(Cloth cloth, Size size, Integer count) {
        this.cloth = cloth;
        this.size = size;
        this.count = count;
    }

    public ClothSizes() {

    }
}
