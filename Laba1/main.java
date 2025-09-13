package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //конструктор
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
            Delivery delivery1 = new Delivery(
                    1,
                    10,
                    100,
                    LocalDate.of(2025, 9, 12),
                    LocalDate.of(2025, 9, 15)
            );
            System.out.println("Успешно создан Delivery через конструктор:");
            System.out.println("Полная версия: " + delivery1);
            System.out.println("Краткая версия: " + delivery1.toShortString());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при создании Delivery: " + e.getMessage());
        }

        System.out.println("\n");

        //CSV
        try {
            String csv = "2,20,200,2025-10-01,2025-10-05";
            Delivery delivery2 = new Delivery(csv);
            System.out.println("Успешно создан Delivery через CSV строку:");
            System.out.println("Полная версия: " + delivery2);
            System.out.println("Краткая версия: " + delivery2.toShortString());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при создании Delivery из CSV: " + e.getMessage());
        }

        System.out.println("\n");


        //JSON
        try {
            String jsonFilePath = "C:/Users/user/Desktop/123.json";
            Delivery delivery4 = new Delivery(jsonFilePath, true);
            System.out.println("Успешно создан Delivery через JSON с указанного пути:");
            System.out.println("Полная версия: " + delivery4);
            System.out.println("Краткая версия: " + delivery4.toShortString());
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n");

        Delivery d1 = new Delivery(3, 30, 300,
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 11, 5));

        Delivery d2 = new Delivery(3, 30, 300,
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 11, 5));

        Delivery d3 = new Delivery(4, 40, 400,
                LocalDate.of(2025, 12, 1),
                LocalDate.of(2025, 12, 10));

        System.out.println("Сравнение объектов:");
        System.out.println("d1.equals(d2) " + (d1.equals(d2) ? "Объекты равны" : "Объекты разные"));
        System.out.println("d1.equals(d3) " + (d1.equals(d3) ? "Объекты равны" : "Объекты разные"));

        System.out.println("\n");

        //ошибки
        try {
            Delivery delivery5 = new Delivery(
                    -5,
                    10,
                    100,
                    LocalDate.of(2025, 9, 12),
                    LocalDate.of(2025, 9, 15)
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Ожидаемая ошибка (deliveryId): " + e.getMessage());
        }

        try {
            Delivery delivery6 = new Delivery(
                    6,
                    11,
                    101,
                    LocalDate.of(2025, 9, 15),
                    LocalDate.of(2025, 9, 12)
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Ожидаемая ошибка (arrivalDate): " + e.getMessage());
        }
    }
}
