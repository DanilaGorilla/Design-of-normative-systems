package org.example.web.controller;

import org.example.Driver;
import org.example.config.AppConfig;
import org.example.web.view.DriverFormView;
import javax.swing.*;


public class DriverEditController {
    private DriverFormView view;
    private final MainController mainController;
    private final int driverId;
    private final Driver originalDriver;

    public DriverEditController(MainController mainController, int driverId) {
        this.mainController = mainController;
        this.driverId = driverId;
        this.originalDriver = AppConfig.getDriverRepository().getById(driverId);

        if (originalDriver == null) {
            throw new IllegalArgumentException("Водитель с ID " + driverId + " не найден");
        }
    }

    public void showForm() {
        if (view == null) {
            view = new DriverFormView(this, "Редактирование водителя", originalDriver);
        }
        view.setVisible(true);
    }


    public void onSave(String firstName, String middleName, String lastName,
                       String experienceStr, String paymentStr) {

        try {
            // Валидация данных (такая же как при добавлении)
            validateInput(firstName, middleName, lastName, experienceStr, paymentStr);

            // Парсим числовые значения
            int experience = Integer.parseInt(experienceStr.trim());
            double payment = Double.parseDouble(paymentStr.trim());

            // Создаем обновленного водителя
            Driver updatedDriver = new Driver(driverId, firstName, middleName,
                    lastName, experience, payment);

            // Проверяем, не пытаемся ли изменить на уже существующего водителя
            if (isDuplicateDriver(updatedDriver)) {
                JOptionPane.showMessageDialog(view,
                        "Ошибка: водитель с такими данными уже существует!",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Обновляем через репозиторий
            boolean success = AppConfig.getDriverRepository().replaceById(driverId, updatedDriver);

            if (success) {
                // Уведомляем издателя об обновлении
                AppConfig.getDriverPublisher().notifyDriverUpdated(updatedDriver);

                // Закрываем форму
                view.dispose();

                // Показываем сообщение об успехе
                JOptionPane.showMessageDialog(view,
                        "Данные водителя успешно обновлены!",
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view,
                        "Не удалось обновить данные водителя.",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            // Показываем ошибку валидации
            JOptionPane.showMessageDialog(view,
                    "Ошибка валидации: " + e.getMessage(),
                    "Ошибка ввода",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    public void onCancel() {
        if (view != null) {
            view.dispose();
        }
    }

    private boolean isDuplicateDriver(Driver updatedDriver) {
        // Получаем всех водителей из репозитория
        var repository = AppConfig.getDriverRepository();

        // Используем декоратор для проверки существования
        try {
            // Проверяем, есть ли водитель с такими же данными (кроме ID)
            var allDrivers = repository.get_k_n_short_list(1000, 1); // Большое число чтобы получить всех

            for (Driver existingDriver : allDrivers) {
                // Пропускаем самого себя (водителя с тем же ID)
                if (existingDriver.getDriverId() == driverId) {
                    continue;
                }

                // Проверяем совпадение всех полей
                if (existingDriver.getFirstName().equals(updatedDriver.getFirstName()) &&
                        existingDriver.getMiddleName().equals(updatedDriver.getMiddleName()) &&
                        existingDriver.getLastName().equals(updatedDriver.getLastName()) &&
                        existingDriver.getExperience() == updatedDriver.getExperience() &&
                        Math.abs(existingDriver.getPayment() - updatedDriver.getPayment()) < 0.01) {

                    return true;
                }
            }
        } catch (Exception e) {
            // В случае ошибки считаем, что дубликата нет
            System.err.println("Ошибка при проверке дубликатов: " + e.getMessage());
        }

        return false;
    }

    private void validateInput(String firstName, String middleName, String lastName,
                               String experience, String payment) {

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Фамилия не может быть пустой");
        }

        // Валидация имени
        validateName(firstName.trim(), "Имя");
        validateName(middleName != null ? middleName.trim() : "", "Отчество");
        validateName(lastName.trim(), "Фамилия");

        // Валидация числовых полей
        if (experience == null || experience.trim().isEmpty()) {
            throw new IllegalArgumentException("Опыт работы не может быть пустым");
        }
        if (payment == null || payment.trim().isEmpty()) {
            throw new IllegalArgumentException("Оплата не может быть пустой");
        }

        // Проверяем, можно ли спарсить experience
        try {
            int exp = Integer.parseInt(experience.trim());
            if (exp < 0) {
                throw new IllegalArgumentException("Опыт работы не может быть отрицательным");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Опыт работы должен быть целым числом");
        }

        // Проверяем, можно ли спарсить payment
        try {
            double pay = Double.parseDouble(payment.trim());
            if (pay < 0) {
                throw new IllegalArgumentException("Оплата не может быть отрицательной");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Оплата должна быть числом");
        }
    }

    private void validateName(String name, String fieldName) {
        if (name.length() > 50) {
            throw new IllegalArgumentException(fieldName + " слишком длинное (макс. 50 символов)");
        }
        if (!name.matches("^[a-zA-Zа-яА-ЯёЁ\\s\\-']+$") && !name.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " содержит недопустимые символы");
        }
    }

    public int getDriverId() {
        return driverId;
    }

    public Driver getOriginalDriver() {
        return originalDriver;
    }
}
