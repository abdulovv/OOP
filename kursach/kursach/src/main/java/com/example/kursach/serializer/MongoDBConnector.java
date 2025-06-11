package com.example.kursach.serializer;

import com.example.kursach.db.entity.Cloth; // Убедитесь, что путь к Cloth правильный
import com.example.kursach.serializer.JsonSerializer; // Импортируем JsonSerializer
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import org.bson.Document;
import org.springframework.stereotype.Component; // Важно для Spring

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime; // Для добавления временной метки


@Component // Это делает класс Spring-компонентом
public class MongoDBConnector {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> clothCollection;

    public MongoDBConnector() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.WARNING); // Уменьшаем уровень логирования драйвера MongoDB

        String uri = "mongodb://localhost:27017/"; // Ваша строка подключения к MongoDB
        MongoClient client = null;

        try {
            client = MongoClients.create(uri);
            // Проверка соединения, пингуем любую базу данных
            client.getDatabase("admin").runCommand(new Document("ping", 1));
            System.out.println("MongoDBConnector: Успешно подключено к MongoDB!");
        } catch (MongoException e) {
            System.err.println("MongoDBConnector: Ошибка при подключении к MongoDB: " + e.getMessage());
            e.printStackTrace();
            // В случае критической ошибки подключения, выбросим RuntimeException
            throw new RuntimeException("Failed to connect to MongoDB", e);
        }
        this.mongoClient = client;
        this.database = mongoClient.getDatabase("myAppDB"); // Название вашей базы данных
        this.clothCollection = database.getCollection("clothes_backup"); // Отдельная коллекция для бэкапа
    }


    public void saveClothes(List<Cloth> clothes, JsonSerializer jsonSerializer) {
        if (clothes == null || clothes.isEmpty()) {
            System.out.println("MongoDBConnector: Нет данных для сохранения в MongoDB.");
            return;
        }

        List<Document> documents = new ArrayList<>();
        for (Cloth cloth : clothes) {
            // Преобразуем объект Cloth в JSON строку
            String jsonString = jsonSerializer.toJson(cloth);
            // Создаем BSON Document из JSON строки
            Document doc = Document.parse(jsonString);
            // Добавляем временную метку, чтобы видеть, когда данные были сохранены
            doc.append("savedAt", LocalDateTime.now().toString()); // Или использовать Date
            documents.add(doc);
        }

        try {
            // Опционально: если вы хотите очищать коллекцию перед каждой записью,
            // но для "дополнительно" это обычно не нужно, иначе будет перезапись.
            // clothCollection.deleteMany(new Document());

            // Вставляем все документы
            clothCollection.insertMany(documents);
            System.out.println("MongoDBConnector: Успешно добавлено " + documents.size() + " документов одежды в коллекцию 'clothes_backup'.");
        } catch (MongoException e) {
            System.err.println("MongoDBConnector: Ошибка при сохранении одежды в MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Если вам понадобится загружать данные из MongoDB
    public List<Cloth> loadAllClothes(JsonSerializer jsonSerializer) {
        List<Cloth> clothes = new ArrayList<>();
        try {
            for (Document doc : clothCollection.find()) {
                // Исключаем поле 'savedAt' или другие метаданные, если они не являются частью Cloth
                doc.remove("_id"); // Удаляем _id, так как это внутренний ID MongoDB
                doc.remove("savedAt"); // Удаляем метаданные, которые добавили при сохранении

                String jsonString = doc.toJson();
                clothes.add(jsonSerializer.fromJson(jsonString, Cloth.class));
            }
            System.out.println("MongoDBConnector: Загружено " + clothes.size() + " документов одежды из коллекции 'clothes_backup'.");
        } catch (MongoException e) {
            System.err.println("MongoDBConnector: Ошибка при загрузке одежды из MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
        return clothes;
    }


    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("🔗 MongoDBConnector: Соединение с MongoDB закрыто.");
        }
    }
}