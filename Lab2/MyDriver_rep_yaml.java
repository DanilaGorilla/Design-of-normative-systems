package org.example;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.*;

public class MyDriver_rep_yaml {

    private List<Driver> drivers = new ArrayList<>();
    private final String filePath;

    public MyDriver_rep_yaml(String filePath) {
        this.filePath = filePath;
        readAll(); // при создании сразу читаем данные из файла
    }
    // ============================================================
    // a. Чтение всех значений из YAML файла
    // ============================================================
    @SuppressWarnings("unchecked")
    public void readAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Файл не найден. Создан пустой список водителей.");
            drivers = new ArrayList<>();
            return;
        }

        try (InputStream input = new FileInputStream(file)) {
            Yaml yaml = new Yaml();
            List<Map<String, Object>> list = yaml.load(input);

            if (list == null) {
                drivers = new ArrayList<>();
                return;
            }

            drivers = new ArrayList<>();
            for (Map<String, Object> item : list) {
                int id = ((Number) item.get("driverId")).intValue();
                String firstName = Objects.toString(item.get("firstName"), "");
                String middleName = Objects.toString(item.get("middleName"), "");
                String lastName = Objects.toString(item.get("lastName"), "");
                int experience = ((Number) item.get("experience")).intValue();
                double payment = ((Number) item.get("payment")).doubleValue();

                drivers.add(new Driver(id, firstName, middleName, lastName, experience, payment));
            }
        } catch (Exception e) {
            System.out.println("Ошибка при чтении YAML: " + e.getMessage());
            drivers = new ArrayList<>();
        }
    }
    // ============================================================
    // b. Запись всех значений в YAML файл
    // ============================================================
    public void writeAll() {
        try (Writer writer = new FileWriter(filePath)) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);

            Yaml yaml = new Yaml(options);
            yaml.dump(drivers, writer);
        } catch (IOException e) {
            System.out.println("Ошибка при записи YAML: " + e.getMessage());
        }
    }
    // ============================================================
    // c. Получить объект по ID
    // ============================================================
    public Driver getById(int id) {
        for (Driver d : drivers) {
            if (d.getDriverId() == id) {
                return d;
            }
        }
        return null;
    }
    // ============================================================
    // d. Получить список k по счёту n объектов (постранично)
    // ============================================================
    public List<Driver> get_k_n_short_list(int k, int n) {
        int start = (n - 1) * k;
        int end = Math.min(start + k, drivers.size());
        if (start >= drivers.size()) return new ArrayList<>();
        return drivers.subList(start, end);
    }
    // ============================================================
    // e. Сортировка по выбранному полю (по стажу)
    // ============================================================
    public void sortByExperience() {
        drivers.sort(Comparator.comparingInt(Driver::getExperience));
    }
    // Альтернативные сортировки
    public void sortByPayment() {
        drivers.sort(Comparator.comparingDouble(Driver::getPayment));
    }

    public void sortByLastName() {
        drivers.sort(Comparator.comparing(Driver::getLastName));
    }
    // ============================================================
    // f. Добавление нового объекта (с формированием нового ID)
    // ============================================================
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
    // ============================================================
    // g. Замена элемента по ID
    // ============================================================
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
    // ============================================================
    // h. Удаление элемента по ID
    // ============================================================
    public boolean deleteById(int id) {
        boolean removed = drivers.removeIf(d -> d.getDriverId() == id);
        if (removed) writeAll();
        return removed;
    }
    // ============================================================
    // i. Получить количество элементов
    // ============================================================
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
