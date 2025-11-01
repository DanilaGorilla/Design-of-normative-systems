package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Main2_2 {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);

        // Работаем через адаптер, а не напрямую с MyDriver_rep_DB
        MyDriver_rep_DB_Adapter repo = new MyDriver_rep_DB_Adapter("");

        try {
            System.out.println("\n=== МЕНЮ УПРАВЛЕНИЯ ВОДИТЕЛЯМИ (PostgreSQL) ===");

            while (true) {
                System.out.println("\nВыберите действие:");
                System.out.println("1 - Показать список водителей (постранично)");
                System.out.println("2 - Найти водителя по ID");
                System.out.println("3 - Добавить нового водителя");
                System.out.println("4 - Изменить данные водителя по ID");
                System.out.println("5 - Удалить водителя по ID");
                System.out.println("6 - Показать количество водителей");
                System.out.println("0 - Выход");
                System.out.print("Ваш выбор: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Введите количество элементов на странице (k): ");
                        int k = scanner.nextInt();
                        System.out.print("Введите номер страницы (n): ");
                        int n = scanner.nextInt();
                        scanner.nextLine();

                        List<Driver> drivers = repo.get_k_n_short_list(k, n);
                        if (drivers.isEmpty()) {
                            System.out.println("Список пуст.");
                        } else {
                            System.out.println("\n=== Список водителей ===");
                            for (Driver d : drivers) {
                                System.out.println(d.toString());
                            }
                        }
                    }

                    case 2 -> {
                        System.out.print("Введите ID водителя: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        Driver driver = repo.getById(id);
                        if (driver != null) {
                            System.out.println("\nНайден водитель:");
                            System.out.println(driver.toString());
                        } else {
                            System.out.println("Водитель с таким ID не найден.");
                        }
                    }

                    case 3 -> {
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
                        scanner.nextLine();

                        Driver newDriver = new Driver(0, firstName, middleName, lastName, experience, payment);
                        repo.addDriver(newDriver);
                        System.out.println("Водитель успешно добавлен в базу данных!");
                    }

                    case 4 -> {
                        System.out.print("Введите ID водителя для изменения: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Введите новое имя: ");
                        String firstName = scanner.nextLine();

                        System.out.print("Введите новое отчество: ");
                        String middleName = scanner.nextLine();

                        System.out.print("Введите новую фамилию: ");
                        String lastName = scanner.nextLine();

                        System.out.print("Введите новый опыт (в годах): ");
                        int experience = scanner.nextInt();

                        System.out.print("Введите новую оплату (в рублях): ");
                        double payment = scanner.nextDouble();
                        scanner.nextLine();

                        Driver updatedDriver = new Driver(id, firstName, middleName, lastName, experience, payment);
                        boolean updated = repo.replaceById(id, updatedDriver);

                        if (updated)
                            System.out.println("Данные успешно обновлены.");
                        else
                            System.out.println("Водитель с таким ID не найден.");
                    }

                    case 5 -> {
                        System.out.print("Введите ID водителя для удаления: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        boolean deleted = repo.deleteById(id);
                        if (deleted)
                            System.out.println("Водитель успешно удалён.");
                        else
                            System.out.println("Водитель с таким ID не найден.");
                    }

                    case 6 -> {
                        int count = repo.getCount();
                        System.out.println("Всего водителей в базе: " + count);
                    }

                    case 0 -> {
                        System.out.println("Завершение работы программы...");
                        return;
                    }

                    default -> System.out.println("Некорректный выбор. Попробуйте снова.");
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
