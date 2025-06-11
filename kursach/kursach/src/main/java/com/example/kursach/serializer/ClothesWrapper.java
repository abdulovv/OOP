package com.example.kursach.serializer;

import com.example.kursach.db.entity.Cloth;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "clothes")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClothesWrapper {

    @XmlElement(name = "cloth")
    private List<Cloth> clothes;

    public ClothesWrapper() {
        // Обязательный пустой конструктор для JAXB
    }

    public ClothesWrapper(List<Cloth> clothes) {
        this.clothes = clothes;
    }

    public List<Cloth> getClothes() {
        return clothes;
    }

    public void setClothes(List<Cloth> clothes) {
        this.clothes = clothes;
    }
}