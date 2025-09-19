package org.example;

import java.util.Scanner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
            System.out.print("Введите ID водителя (целое число): ");
            int driverId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Введите имя: ");
            String firstName = scanner.nextLine();

            System.out.print("Введите отчество: ");
            String middleName = scanner.nextLine();

            System.out.print("Введите фамилию: ");
            String lastName = scanner.nextLine();

            System.out.print("Введите опыт (в годах): ");
            int experience = scanner.nextInt();

            System.out.print("Введите оплату(в рублях): ");
            double payment = scanner.nextDouble();

            Driver driver = new Driver(driverId, firstName, middleName, lastName, experience, payment);

            System.out.println("Водитель успешно создан!");
            System.out.println("ID: " + driver.getDriverId());
            System.out.println("ФИО: " + driver.getLastName() + " " + driver.getFirstName() + " " + driver.getMiddleName());
            System.out.println("Опыт: " + driver.getExperience() + " лет");
            System.out.println("Оплата: " + driver.getPayment());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неверный ввод: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
