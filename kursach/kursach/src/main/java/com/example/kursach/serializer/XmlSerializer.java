package com.example.kursach.serializer;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class XmlSerializer {

    // Сериализация объекта в XML файл
    public void toXmlFile(Object object, String filePath) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Форматирование XML для читаемости
        marshaller.marshal(object, new File(filePath));
    }

    // Десериализация объекта из XML файла
    public <T> T fromXmlFile(String filePath, Class<T> clazz) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new File(filePath));
    }
}