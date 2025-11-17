package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.Comparator;

public class Main2 {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
        MyDriver_rep_json jsonRepo = new MyDriver_rep_json("src/main/java/org/example/drivers.json");
        FileRepositoryFilterSortDecorator repo = new FileRepositoryFilterSortDecorator(jsonRepo);

        while (true) {
            System.out.println("\n=== МЕНЮ УПРАВЛЕНИЯ ВОДИТЕЛЯМИ ===");
            System.out.println("1. Показать всех водителей");
            System.out.println("2. Добавить нового водителя");
            System.out.println("3. Найти водителя по ID");
            System.out.println("4. Удалить водителя по ID");
            System.out.println("5. Заменить водителя по ID");
            System.out.println("6. Сортировать по опыту");
            System.out.println("7. Получить часть списка (пагинация)");
            System.out.println("8. Показать количество водителей");
            System.out.println("9. Показать список с фильтрацией и сортировкой");
            System.out.println("10. Показать количество водителей с фильтром");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.println("\n=== Список водителей ===");
                    repo.printAll();
                }
                case 2 -> {
                    System.out.print("Имя: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Отчество: ");
                    String middleName = scanner.nextLine();

                    System.out.print("Фамилия: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Опыт (в годах): ");
                    int experience = scanner.nextInt();

                    System.out.print("Оплата (в рублях): ");
                    double payment = scanner.nextDouble();

                    boolean added = repo.addDriver(new Driver(0, firstName, middleName, lastName, experience, payment));
                    if (added) {
                        System.out.println("Водитель успешно добавлен!");
                    }

                }
                case 3 -> {
                    System.out.print("Введите ID водителя: ");
                    int id = scanner.nextInt();
                    Driver d = repo.getById(id);
                    if (d != null) {
                        System.out.println("Найден водитель: " + d);
                    } else {
                        System.out.println("Водитель с таким ID не найден.");
                    }
                }
                case 4 -> {
                    System.out.print("Введите ID водителя для удаления: ");
                    int id = scanner.nextInt();
                    boolean deleted = repo.deleteById(id);
                    if (deleted) {
                        System.out.println("Водитель удалён.");
                    } else {
                        System.out.println("Водитель с таким ID не найден.");
                    }
                }
                case 5 -> {
                    System.out.print("Введите ID водителя для замены: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Имя: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Отчество: ");
                    String middleName = scanner.nextLine();

                    System.out.print("Фамилия: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Опыт (в годах): ");
                    int experience = scanner.nextInt();

                    System.out.print("Оплата (в рублях): ");
                    double payment = scanner.nextDouble();

                    boolean replaced = repo.replaceById(id, new Driver(id, firstName, middleName, lastName, experience, payment));
                    if (replaced) {
                        System.out.println("Водитель успешно заменён.");
                    } else {
                        System.out.println("Водитель с таким ID не найден или данные совпадают с существующим водителем.");
                    }
                }
                case 6 -> {
                    List<Driver> sortedDrivers = repo.get_k_n_short_list(
                            repo.getCount(),
                            1,
                            null, // без фильтра
                            Comparator.comparingInt(Driver::getExperience)
                    );
                    System.out.println("Список отсортирован по опыту.");
                    sortedDrivers.forEach(System.out::println);
                }
                case 7 -> {
                    System.out.print("Введите размер страницы (k): ");
                    int k = scanner.nextInt();

                    System.out.print("Введите номер страницы (n): ");
                    int n = scanner.nextInt();

                    List<Driver> part = repo.get_k_n_short_list(k, n);
                    if (part.isEmpty()) System.out.println("Такой страницы нет (пустой результат).");
                    else {
                        System.out.println("=== Страница №" + n + " ===");
                        for (Driver d : part) System.out.println(d);
                    }
                }
                case 8 -> {
                    System.out.println("Всего водителей: " + repo.getCount());
                }
                case 9 -> {
                    System.out.print("Введите количество элементов на странице (k): ");
                    int k = scanner.nextInt();
                    System.out.print("Введите номер страницы (n): ");
                    int n = scanner.nextInt();
                    scanner.nextLine();

                    Predicate<Driver> filter = driver -> driver.getExperience() > 3;
                    Comparator<Driver> sorter = Comparator.comparingDouble(Driver::getPayment).reversed(); 

                    List<Driver> drivers = repo.get_k_n_short_list(k, n, filter, sorter);
                    if (drivers.isEmpty()) {
                        System.out.println("Список пуст.");
                    } else {
                        System.out.println("\n=== Отфильтрованный и отсортированный список водителей ===");
                        for (Driver d : drivers) {
                            System.out.println(d.toString());
                        }
                    }
                }

                case 10 -> {
                    Predicate<Driver> filter = driver -> driver.getPayment() > 30000;
                    int count = repo.getCount(filter);
                    System.out.println("Водителей с зарплатой более 30000: " + count);
                }
                case 0 -> {
                    System.out.println("Выход из программы...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Некорректный выбор. Повторите ввод.");
            }
        }
    }
}
