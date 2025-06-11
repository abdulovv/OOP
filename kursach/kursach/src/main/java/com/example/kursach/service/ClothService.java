// com/example/kursach/service/ClothService.java
package com.example.kursach.service;

import com.example.kursach.db.entity.Cloth;
import com.example.kursach.db.entity.ClothSize;
import com.example.kursach.db.entity.repository.ClothRepository;
import com.example.kursach.db.entity.repository.ClothSizeRepository;
import com.example.kursach.domain.Category;
import com.example.kursach.domain.Sex;
import com.example.kursach.serializer.ClothesWrapper;
import com.example.kursach.serializer.JsonSerializer;
import com.example.kursach.serializer.MongoDBConnector;
import com.example.kursach.serializer.XmlSerializer;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClothService {
    private final ClothSizeRepository clothSizeRepository;
    private final ClothRepository clothRepository;
    private final JsonSerializer jsonSerializer;
    private final XmlSerializer xmlSerializer;
    private final MongoDBConnector mongoDBConnector;

    @Autowired
    public ClothService(ClothRepository clothRepository, ClothSizeRepository clothSizeRepository, JsonSerializer jsonSerializer, XmlSerializer xmlSerializer, MongoDBConnector mongoDBConnector) {
        this.clothRepository = clothRepository;
        this.clothSizeRepository = clothSizeRepository;
        this.jsonSerializer = jsonSerializer;
        this.xmlSerializer = new XmlSerializer();
        this.mongoDBConnector = mongoDBConnector;
    }

    public List<Cloth> getAllClothes(String gender, String category, String size, String sort, String order, String query) throws IOException {
        List<Cloth> clothes = clothRepository.findAll();

        if (gender != null && !gender.isEmpty()) {
            try {
                Sex sex = Sex.valueOf(gender.toUpperCase());
                clothes = clothes.stream()
                        .filter(cloth -> cloth.getSex() == sex)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка при преобразовании пола: " + gender);
            }
        }

        if (category != null && !category.isEmpty()) {
            try {
                Category cat = Category.valueOf(category);
                clothes = clothes.stream()
                        .filter(cloth -> cloth.getCategory() == cat)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка при преобразовании категории: " + category);
            }
        }

        if (size != null && !size.isEmpty()) {
            clothes = clothes.stream()
                    .filter(cloth -> {
                        List<ClothSize> sizes = clothSizeRepository.findClothSizesByCloth_Id(cloth.getId());
                        return sizes.stream().anyMatch(clothSize -> clothSize.getSize().name().equalsIgnoreCase(size) && clothSize.getCount() > 0);
                    })
                    .collect(Collectors.toList());
        }

        if (sort != null && sort.equals("price")) {
            if (order != null && order.equalsIgnoreCase("asc")) {
                clothes.sort(Comparator.comparing(Cloth::getPrice));
            } else if (order != null && order.equalsIgnoreCase("desc")) {
                clothes.sort(Comparator.comparing(Cloth::getPrice).reversed());
            }
        }

        if (query != null && !query.isEmpty()) {
            String lowerCaseQuery = query.toLowerCase();
            clothes = clothes.stream()
                    .filter(cloth -> (
                            cloth.getName() != null &&
                                    cloth.getName().toLowerCase().contains(lowerCaseQuery))
                    )
                    .collect(Collectors.toList());
        }

        //SERIAL

        String jsonFilePath = "D:\\OOP\\kursach\\kursach\\src\\main\\java\\com\\example\\kursach\\RESPONCE\\JSONRESPONCE.json";
        String xmlFilePath = "D:\\OOP\\kursach\\kursach\\src\\main\\java\\com\\example\\kursach\\RESPONCE\\XMLRESPONCE.xml";

        try {
            // 1. Сохранение в JSON файл (уже было)
            saveClothesToJson(clothes, jsonFilePath);

            // 2. Сохранение в XML файл (уже было)
            saveClothesToXml(clothes, xmlFilePath);

            // 3. Дополнительное сохранение в MongoDB!
            mongoDBConnector.saveClothes(clothes, jsonSerializer);

        } catch (IOException | JAXBException e) {
            e.printStackTrace(); // Вывод информации об ошибке
        }

        return clothes;
    }



    public void saveClothesToJson(List<Cloth> clothes, String filePath) throws IOException {
        jsonSerializer.toJsonFile(clothes, filePath); // Сериализуем весь список
    }
    public void saveClothesToXml(List<Cloth> clothes, String filePath) throws JAXBException, IOException {
        //  Для XML нам нужен "корневой" элемент, поэтому создадим обертку
        ClothesWrapper wrapper = new ClothesWrapper(clothes);
        xmlSerializer.toXmlFile(wrapper, filePath);
    }


    private List<Cloth> getClothesByCategory(String category) {
        try {
            Category cat = Category.valueOf(category.toUpperCase());
            return clothRepository.findByCategory(cat);
        } catch (IllegalArgumentException e) {
            return clothRepository.findAll();
        }
    }

    //SERIAL


    public Cloth getClothById(int id) {
        return clothRepository.findById(id).orElse(null);
    }

    public List<Object> getSizesForCloth(int clothId) {
        return clothSizeRepository.findClothSizesByCloth_Id(clothId)
                .stream()
                .map(clothSize -> new Object() {
                    public String size = clothSize.getSize().name();
                    public Integer count = clothSize.getCount();
                })
                .collect(Collectors.toList());
    }

    //serial

    public void saveClothToJson(Cloth cloth, String filePath) throws IOException {
        JsonSerializer jsonSerializer = null;
        jsonSerializer.toJsonFile(cloth, filePath);
    }

    public Cloth loadClothFromJson(String filePath) throws IOException {
        JsonSerializer jsonSerializer = null;
        return jsonSerializer.fromJsonFile(filePath, Cloth.class);
    }

    public void saveClothToXml(Cloth cloth, String filePath) throws JAXBException, IOException {
        XmlSerializer xmlSerializer = null;
        xmlSerializer.toXmlFile(cloth, filePath);
    }

    public Cloth loadClothFromXml(String filePath) throws JAXBException, IOException {
        XmlSerializer xmlSerializer = null;
        return xmlSerializer.fromXmlFile(filePath, Cloth.class);
    }
}