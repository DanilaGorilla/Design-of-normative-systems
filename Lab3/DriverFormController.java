package org.example.web.controller;

//Общий интерфейс для контроллеров формы водителя(Для добавления и редактирования)

public interface DriverFormController {
    //Вызывается при сохранении формы

    void onSave(String firstName, String middleName, String lastName,
                String experienceStr, String paymentStr);

    //Вызывается при отмене

    void onCancel();

    //Возвращает заголовок окна

    String getFormTitle();

    //Нужно ли заполнять форму при открытии

    boolean shouldPreFillForm();

    // Заполняет форму данными (для редактирования)

    void fillForm(org.example.Driver driver);
}
