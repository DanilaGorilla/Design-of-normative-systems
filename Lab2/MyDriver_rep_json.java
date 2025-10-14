package org.example;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;

public class MyDriver_rep_json {

    private List<Driver> drivers = new ArrayList<>();
    private final String filePath;

    public MyDriver_rep_json(String filePath) {
        this.filePath = filePath;
        readAll(); // при создании сразу читаем данные из файла
    }

    // a. Чтение всех значений из JSON файла
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
    public void writeAll() {
        try (Writer writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(drivers, writer);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    // c. Получить объект по ID
    public Driver getById(int id) {
        for (Driver d : drivers) {
            if (d.getDriverId() == id) {
                return d;
            }
        }
        return null;
    }

    // d. Получить список k по счёту n объектов (постранично)

    public List<Driver> get_k_n_short_list(int k, int n) {
        int start = (n - 1) * k;
        int end = Math.min(start + k, drivers.size());
        if (start >= drivers.size()) return new ArrayList<>();
        return drivers.subList(start, end);
    }

    // e. Сортировка по выбранному полю (по стажу)

    public void sortByExperience() {
        drivers.sort(Comparator.comparingInt(Driver::getExperience));
    }

    // Можно добавить альтернативные методы сортировки
    public void sortByPayment() {
        drivers.sort(Comparator.comparingDouble(Driver::getPayment));
    }

    public void sortByLastName() {
        drivers.sort(Comparator.comparing(Driver::getLastName));
    }

    // f. Добавление нового объекта (с формированием нового ID)
    public void addDriver(Driver driver) {
        int newId = drivers.stream()
                .mapToInt(Driver::getDriverId)
                .max()
                .orElse(0) + 1;

        Driver newDriver = new Driver(
                newId,
                driver.getFirstName(),
                driver.getMiddleName(),
                driver.getLastName(),
                driver.getExperience(),
                driver.getPayment()
        );

        drivers.add(newDriver);
        writeAll();
    }

    // g. Замена элемента по ID

    public boolean replaceById(int id, Driver newDriver) {
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getDriverId() == id) {
                Driver updated = new Driver(
                        id,
                        newDriver.getFirstName(),
                        newDriver.getMiddleName(),
                        newDriver.getLastName(),
                        newDriver.getExperience(),
                        newDriver.getPayment()
                );
                drivers.set(i, updated);
                writeAll();
                return true;
            }
        }
        return false;
    }

    // h. Удаление элемента по ID
    public boolean deleteById(int id) {
        boolean removed = drivers.removeIf(d -> d.getDriverId() == id);
        if (removed) writeAll();
        return removed;
    }

    // i. Получить количество элементов
    public int getCount() {
        return drivers.size();
    }

    public void printAll() {
        if (drivers.isEmpty()) {
            System.out.println("Список водителей пуст.");
            return;
        }
        for (Driver d : drivers) {
            System.out.println(d);
        }
    }

    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers);
    }
}

