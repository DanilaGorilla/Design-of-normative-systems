// web/controller/DriverEditController.java
package org.example.web.controller;

import org.example.Driver;
import org.example.config.AppConfig;
import org.example.web.view.DriverFormView;
import javax.swing.*;

/**
 * Контроллер для редактирования существующего водителя (MVC)
 * Реализует интерфейс DriverFormController
 */
public class DriverEditController implements DriverFormController {
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
            view = new DriverFormView(this);
        }
        view.fillForm(originalDriver);
        view.setVisible(true);
    }

    @Override
    public void onSave(String firstName, String middleName, String lastName,
                       String experienceStr, String paymentStr) {

        try {
            // Валидация данных
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
                if (view != null) {
                    view.disposeView();
                }

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

    @Override
    public void onCancel() {
        if (view != null) {
            int result = JOptionPane.showConfirmDialog(
                    view,
                    "Вы действительно хотите отменить редактирование? Все несохраненные изменения будут потеряны.",
                    "Подтверждение отмены",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                view.disposeView();
            }
        }
    }

    @Override
    public String getFormTitle() {
        return "Редактирование водителя (ID: " + driverId + ")";
    }

    @Override
    public boolean shouldPreFillForm() {
        return true; // При редактировании форма должна быть заполнена
    }

    @Override
    public void fillForm(Driver driver) {
        if (view != null) {
            view.fillForm(driver);
        }
    }

    // ==================== Вспомогательные методы ====================

    private boolean isDuplicateDriver(Driver updatedDriver) {
        var repository = AppConfig.getDriverRepository();

        try {
            var allDrivers = repository.get_k_n_short_list(1000, 1);

            for (Driver existingDriver : allDrivers) {
                if (existingDriver.getDriverId() == driverId) {
                    continue;
                }

                if (existingDriver.getFirstName().equals(updatedDriver.getFirstName()) &&
                        existingDriver.getMiddleName().equals(updatedDriver.getMiddleName()) &&
                        existingDriver.getLastName().equals(updatedDriver.getLastName()) &&
                        existingDriver.getExperience() == updatedDriver.getExperience() &&
                        Math.abs(existingDriver.getPayment() - updatedDriver.getPayment()) < 0.01) {
                    return true;
                }
            }
        } catch (Exception e) {
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

        validateName(firstName.trim(), "Имя");
        validateName(middleName != null ? middleName.trim() : "", "Отчество");
        validateName(lastName.trim(), "Фамилия");

        if (experience == null || experience.trim().isEmpty()) {
            throw new IllegalArgumentException("Опыт работы не может быть пустым");
        }
        if (payment == null || payment.trim().isEmpty()) {
            throw new IllegalArgumentException("Оплата не может быть пустой");
        }

        try {
            int exp = Integer.parseInt(experience.trim());
            if (exp < 0) {
                throw new IllegalArgumentException("Опыт работы не может быть отрицательным");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Опыт работы должен быть целым числом");
        }

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
