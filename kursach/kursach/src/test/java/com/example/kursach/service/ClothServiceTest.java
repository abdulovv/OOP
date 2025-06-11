// com/example/kursach/service/ClothServiceTest.java
package com.example.kursach.service;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.db.entity.ClothSize;
import com.example.kursach.db.entity.repository.ClothRepository;
import com.example.kursach.db.entity.repository.ClothSizeRepository;
import com.example.kursach.domain.Category;
import com.example.kursach.domain.Sex;
import com.example.kursach.domain.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClothServiceTest {

    @Mock
    private ClothRepository clothRepository;

    @Mock
    private ClothSizeRepository clothSizeRepository;

    @InjectMocks
    private ClothService clothService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализация Mockito
    }


    @Test
    void getClothById_clothExists_returnsCloth() {
        // Arrange
        Cloth cloth = new Cloth("T-shirt", 25.0, Category.Shirt, Sex.MALE);
        when(clothRepository.findById(1)).thenReturn(Optional.of(cloth));

        // Act
        Cloth result = clothService.getClothById(1);

        // Assert
        assertEquals(cloth, result);
    }

    @Test
    void getClothById_clothDoesNotExist_returnsNull() {
        // Arrange
        when(clothRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Cloth result = clothService.getClothById(1);

        // Assert
        assertNull(result);
    }

    @Test
    void getSizesForCloth_returnsListOfSizesWithCount() {
        // Arrange
        Cloth cloth = new Cloth("T-shirt", 25.0, Category.Shirt, Sex.MALE);
        cloth.setId(1);
        ClothSize clothSize1 = new ClothSize(cloth, Size.M, 10);
        ClothSize clothSize2 = new ClothSize(cloth, Size.L, 5);
        when(clothSizeRepository.findClothSizesByCloth_Id(1)).thenReturn(Arrays.asList(clothSize1, clothSize2));

        // Act
        List<Object> result = clothService.getSizesForCloth(1);

        // Assert
        assertEquals(2, result.size());
        try {
            assertEquals("M", result.get(0).getClass().getField("size").get(result.get(0)));
            assertEquals(10, result.get(0).getClass().getField("count").get(result.get(0)));
            assertEquals("L", result.get(1).getClass().getField("size").get(result.get(1)));
            assertEquals(5, result.get(1).getClass().getField("count").get(result.get(1)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}