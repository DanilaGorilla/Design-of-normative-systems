// web/view/DriverFormView.java
package org.example.web.view;

import org.example.Driver;
import org.example.web.controller.DriverFormController;
import javax.swing.*;
import java.awt.*;

/**
 * Универсальная форма для добавления/редактирования водителя
 */
public class DriverFormView extends JDialog {
    private final DriverFormController controller;

    // Поля формы
    private final JTextField firstNameField;
    private final JTextField middleNameField;
    private final JTextField lastNameField;
    private final JTextField experienceField;
    private final JTextField paymentField;

    // Кнопки
    private final JButton saveButton;
    private final JButton cancelButton;

    // Панель для формы
    private JPanel formPanel;

    public DriverFormView(DriverFormController controller) {
        super((Frame) null, controller.getFormTitle(), true);
        this.controller = controller;

        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        // Создаем поля формы
        firstNameField = new JTextField(20);
        middleNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        experienceField = new JTextField(20);
        paymentField = new JTextField(20);

        saveButton = new JButton("Сохранить");
        cancelButton = new JButton("Отмена");

        initComponents();
        setupLayout();
        setupListeners();

        // Если контроллер сообщает, что нужно заполнить форму
        if (controller.shouldPreFillForm()) {
            controller.fillForm(null); // Контроллер сам знает, как заполнить
        }
    }

    private void initComponents() {
        // Настройка числовых полей
        experienceField.setToolTipText("Опыт работы в годах (целое число)");
        paymentField.setToolTipText("Оплата в рублях (число с дробной частью)");

        // Устанавливаем шрифты
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        firstNameField.setFont(fieldFont);
        middleNameField.setFont(fieldFont);
        lastNameField.setFont(fieldFont);
        experienceField.setFont(fieldFont);
        paymentField.setFont(fieldFont);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Заголовок
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel(controller.getFormTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(30, 30, 100));
        headerPanel.add(titleLabel);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        // Панель с полями формы
        formPanel = new JPanel(new GridLayout(5, 2, 15, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.setBackground(new Color(245, 245, 250));

        // Добавляем поля с метками
        addFormRow("Имя*:", firstNameField);
        addFormRow("Отчество:", middleNameField);
        addFormRow("Фамилия*:", lastNameField);
        addFormRow("Опыт (лет)*:", experienceField);
        addFormRow("Оплата (руб)*:", paymentField);

        // Панель с информацией
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
        infoPanel.add(new JLabel("* - обязательные поля"));

        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Стилизация кнопок
        styleButton(saveButton, new Color(70, 130, 180));
        styleButton(cancelButton, new Color(220, 80, 80));

        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Разделяем на две панели в SOUTH
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(infoPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void addFormRow(String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setOpaque(true);
        field.setBackground(Color.WHITE);

        formPanel.add(label);
        formPanel.add(field);
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(backgroundColor.darker(), 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        // Эффект при наведении
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }

    private void setupListeners() {
        saveButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String middleName = middleNameField.getText();
            String lastName = lastNameField.getText();
            String experience = experienceField.getText();
            String payment = paymentField.getText();

            controller.onSave(firstName, middleName, lastName, experience, payment);
        });

        cancelButton.addActionListener(e -> {
            controller.onCancel();
        });

        // Закрытие окна по крестику
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                controller.onCancel();
            }
        });
    }

    // Методы для заполнения формы
    public void setFirstName(String firstName) {
        firstNameField.setText(firstName);
    }

    public void setMiddleName(String middleName) {
        middleNameField.setText(middleName);
    }

    public void setLastName(String lastName) {
        lastNameField.setText(lastName);
    }

    public void setExperience(String experience) {
        experienceField.setText(experience);
    }

    public void setPayment(String payment) {
        paymentField.setText(payment);
    }

    // Метод для полного заполнения формы
    public void fillForm(Driver driver) {
        if (driver != null) {
            setFirstName(driver.getFirstName());
            setMiddleName(driver.getMiddleName());
            setLastName(driver.getLastName());
            setExperience(String.valueOf(driver.getExperience()));
            setPayment(String.format("%.2f", driver.getPayment()));
        }
    }

    // Метод для очистки формы
    public void clearForm() {
        setFirstName("");
        setMiddleName("");
        setLastName("");
        setExperience("");
        setPayment("");
    }

    public void disposeView() {
        dispose();
    }
}
