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

            // вывод информации
            System.out.println("\nВодитель успешно создан!");

            System.out.println("\n=== Полная версия объекта ===");
            System.out.println(driver.toString());

            System.out.println("\n=== Краткая версия объекта ===");
            System.out.println(driver.toShortString());

            // сравнение объектов (создадим копию для примера)
            Driver driverCopy = new Driver(driver.getDriverId(), driver.getFirstName(),
                    driver.getMiddleName(), driver.getLastName(),
                    driver.getExperience(), driver.getPayment());

            System.out.println("\n=== Сравнение объектов ===");
            if (driver.equals(driverCopy)) {
                System.out.println("Водители равны (содержат одинаковые данные).");
            } else {
                System.out.println("Водители разные.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неверный ввод: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
