package org.example;
import java.time.LocalDate;


public class Main {
    public static <Client> void main(String[] args) {
        //Обычный
        try {
            Delivery delivery1 = new Delivery(
                    1,
                    10,
                    100,
                    LocalDate.of(2025, 9, 12),
                    LocalDate.of(2025, 9, 15)
            );
            System.out.println("Успешно создан Delivery через конструктор:");
            printDelivery(delivery1);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при создании Delivery: " + e.getMessage());
        }

        //CSV
        try {
            String csv = "2,20,200,2025-10-01,2025-10-05";
            Delivery delivery2 = new Delivery(csv);
            System.out.println("Успешно создан Delivery через CSV строку:");
            printDelivery(delivery2);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при создании Delivery из CSV: " + e.getMessage());
        }


        //JSON
        try {
            Delivery delivery4 = new Delivery("delivery.json", true);
            System.out.println("Успешно создан Delivery через JSON файл:");
            printDelivery(delivery4);
        } catch (Exception e) {
            System.out.println("Ошибка при создании Delivery из JSON файла: " + e.getMessage());
        }
        
        try {
            String jsonFilePath = "C:/Users/user/Desktop/123.json";

            Delivery delivery = new Delivery(jsonFilePath, true);

            System.out.println("deliveryId = " + delivery.getDeliveryId());
            System.out.println("routeId = " + delivery.getRouteId());
            System.out.println("driverId = " + delivery.getDriverId());
            System.out.println("departureDate = " + delivery.getDepartureDate());
            System.out.println("arrivalDate = " + delivery.getArrivalDate());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }

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
    private static void printDelivery(Delivery delivery) {
        System.out.println("deliveryId = " + delivery.getDeliveryId());
        System.out.println("routeId = " + delivery.getRouteId());
        System.out.println("driverId = " + delivery.getDriverId());
        System.out.println("departureDate = " + delivery.getDepartureDate());
        System.out.println("arrivalDate = " + delivery.getArrivalDate());
    }
}
