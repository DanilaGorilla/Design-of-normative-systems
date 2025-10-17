package org.example;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.*;

public class MyDriver_rep_json extends MyDriver_rep_base {

    public MyDriver_rep_json(String filePath) {
        super(filePath);
        readAll();
    }
    // a. Чтение всех значений из JSON файла
    @Override
    public void readAll() {
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            drivers = gson.fromJson(reader, new TypeToken<List<Driver>>() {}.getType());
            if (drivers == null) drivers = new ArrayList<>();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. Создан пустой список водителей.");
            drivers = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
    // b. Запись всех значений в JSON файл
    @Override
    public void writeAll() {
        try (Writer writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(drivers, writer);
        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}
