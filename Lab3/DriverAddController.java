package org.example.web.controller;

import org.example.Driver;
import org.example.config.AppConfig;
import org.example.web.view.DriverFormView;
import javax.swing.*;

//Контроллер для добавления нового водителя (MVC) Реализует интерфейс DriverFormController

public class DriverAddController implements DriverFormController {
    private DriverFormView view;
    private final MainController mainController;

    public DriverAddController(MainController mainController) {
        this.mainController = mainController;
    }

    public void showForm() {
        if (view == null) {
            view = new DriverFormView(this);
        }
        view.clearForm();
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

            // Создаем нового водителя с временным ID (0)
            Driver newDriver = new Driver(0, firstName, middleName, lastName, experience, payment);

            // Добавляем через репозиторий
            boolean success = AppConfig.getDriverRepository().addDriver(newDriver);

            if (success) {
                // Уведомляем издателя о новом водителе
                AppConfig.getDriverPublisher().notifyDriverAdded(newDriver);

                // Закрываем форму
                if (view != null) {
                    view.disposeView();
                }

                // Показываем сообщение об успехе
                JOptionPane.showMessageDialog(view,
                        "Водитель успешно добавлен!",
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view,
                        "Не удалось добавить водителя. Возможно, такая запись уже существует.",
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
                    "Вы действительно хотите отменить добавление водителя?",
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
        return "Добавление нового водителя";
    }

    @Override
    public boolean shouldPreFillForm() {
        return false; // При добавлении форма пустая
    }

    @Override
    public void fillForm(Driver driver) {
        // При добавлении не заполняем форму
        if (view != null) {
            view.clearForm();
        }
    }

    // ==================== Вспомогательные методы ====================

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

        int exp = Integer.parseInt(experience.trim());
        if (exp < 0) {
            throw new IllegalArgumentException("Опыт работы не может быть отрицательным");
        }

        double pay = Double.parseDouble(payment.trim());
        if (pay < 0) {
            throw new IllegalArgumentException("Оплата не может быть отрицательной");
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
}
