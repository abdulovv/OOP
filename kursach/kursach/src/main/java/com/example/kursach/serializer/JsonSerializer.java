package com.example.kursach.serializer;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class JsonSerializer {

    public String toJson(Object object) {
        if (object == null) {
            return "null";
        }
        if (object instanceof String) {
            return "\"" + escapeJsonString((String) object) + "\"";
        }
        if (object instanceof Number || object instanceof Boolean) {
            return object.toString();
        }
        if (object.getClass().isEnum()) {
            return "\"" + ((Enum<?>) object).name() + "\"";
        }

        if (object instanceof Collection) {
            StringBuilder collectionJson = new StringBuilder("[");
            boolean firstElement = true;
            for (Object element : (Collection<?>) object) {
                if (!firstElement) {
                    collectionJson.append(",");
                }
                collectionJson.append(toJson(element));
                firstElement = false;
            }
            collectionJson.append("]");
            return collectionJson.toString();
        }


        StringBuilder jsonBuilder = new StringBuilder("{");
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        boolean firstField = true;
        for (Field field : fields) {
            if (field.isSynthetic() || java.lang.reflect.Modifier.isStatic(field.getModifiers()) || java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (!firstField) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("\"").append(field.getName()).append("\":");
                jsonBuilder.append(toJson(value));
                firstField = false;
            } catch (IllegalAccessException e) {
                System.err.println("Ошибка доступа к полю " + field.getName() + ": " + e.getMessage());
            }
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private String escapeJsonString(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\f", "\\f");
    }

    //Десериализация
    public <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty() || "null".equals(json)) {
            return null;
        }

        try {
            if (clazz.isEnum()) {
                String enumName = json.trim().replace("\"", "");
                return (T) Enum.valueOf((Class<Enum>) clazz, enumName);
            }

            T instance = clazz.getDeclaredConstructor().newInstance();

            String content = json.trim();
            if (content.startsWith("{") && content.endsWith("}")) {
                content = content.substring(1, content.length() - 1);
            } else {
                Object convertedValue = convertStringToJsonType(json, clazz);
                if (convertedValue != null) {
                    return (T) convertedValue;
                }
                throw new IllegalArgumentException("Некорректный формат JSON для объекта: " + json);
            }

            Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*(null|true|false|\"[^\"]*\"|-?\\d+\\.?\\d*|\\[.*\\]|\\{.*\\})"); // Расширили regex для массивов
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);

                try {
                    Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);
                    Object convertedValue = convertStringToJsonType(value, field.getType());
                    field.set(instance, convertedValue);
                } catch (NoSuchFieldException e) {
                    System.err.println("Поле " + key + " не найдено в классе " + clazz.getName());
                } catch (IllegalAccessException e) {
                    System.err.println("Ошибка доступа к полю " + key + ": " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Ошибка при десериализации поля " + key + ": " + e.getMessage());
                }
            }
            return instance;

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Не удалось создать экземпляр класса " + clazz.getName(), e);
        }
    }


    private Object convertStringToJsonType(String jsonValue, Class<?> targetType) {
        jsonValue = jsonValue.trim();

        if ("null".equals(jsonValue)) {
            return null;
        }

        if (targetType.isEnum()) {
            String enumName = jsonValue.replace("\"", "");
            try {
                return Enum.valueOf((Class<Enum>) targetType, enumName);
            } catch (IllegalArgumentException e) { //енам невалидный
                return null;
            }
        }

        if (targetType == String.class) {
            if (jsonValue.startsWith("\"") && jsonValue.endsWith("\"")) {
                return unescapeJsonString(jsonValue.substring(1, jsonValue.length() - 1));
            }
            return jsonValue;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(jsonValue);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(jsonValue);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(jsonValue);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(jsonValue);
        }

        if (jsonValue.startsWith("{") && jsonValue.endsWith("}")) {
            return fromJson(jsonValue, targetType);
        }

        return null;
    }

    private String unescapeJsonString(String s) {
        return s.replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\b", "\b")
                .replace("\\f", "\f");
    }

    public void toJsonFile(Object object, String filePath) throws IOException {
        try (Writer writer = new FileWriter(filePath)) {
            writer.write(toJson(object));
        }
    }

    public <T> T fromJsonFile(String filePath, Class<T> clazz) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        try (Reader reader = new FileReader(filePath)) {
            int character;
            while ((character = reader.read()) != -1) {
                jsonBuilder.append((char) character);
            }
        }
        return fromJson(jsonBuilder.toString(), clazz);
    }
}