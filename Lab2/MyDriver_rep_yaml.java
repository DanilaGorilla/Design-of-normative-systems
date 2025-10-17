package org.example;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.*;

public class MyDriver_rep_yaml extends MyDriver_rep_base {

    public MyDriver_rep_yaml(String filePath) {
        super(filePath);
        readAll();
    }
    // a. Чтение всех значений из YAML файла
    @Override
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
    // b. Запись всех значений в YAML файл
    @Override
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
}
