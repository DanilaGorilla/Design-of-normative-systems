package org.example;

import java.util.Scanner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

            System.out.println("Выберите способ создания водителя:");
            System.out.println("1 - Ввод вручную");
            System.out.println("2 - Из CSV строки");
            System.out.println("3 - Из JSON файла");
            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            Driver driver = null;

            switch (choice) {
                case 1 -> {
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

                    System.out.print("Введите оплату (в рублях): ");
                    double payment = scanner.nextDouble();

                    driver = new Driver(driverId, firstName, middleName, lastName, experience, payment);
                }
                case 2 -> {
                    System.out.print("Введите CSV строку (формат: id,имя,отчество,фамилия,опыт,оплата): ");
                    String csv = scanner.nextLine();
                    driver = new Driver(csv);
                }
                case 3 -> {
                    System.out.print("Введите путь к JSON файлу: ");
                    String path = scanner.nextLine();
                    driver = new Driver(path, true);
                }
                default -> {
                    System.out.println("Некорректный выбор.");
                    return;
                }
            }

            System.out.println("Водитель успешно создан!");
            System.out.println("ID: " + driver.getDriverId());
            System.out.println("ФИО: " + driver.getLastName() + " " + driver.getFirstName() + " " + driver.getMiddleName());
            System.out.println("Опыт: " + driver.getExperience() + " лет");
            System.out.println("Оплата: " + driver.getPayment() + " руб.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неверный ввод: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
