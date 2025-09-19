package org.example;

public class DriverBase {

    protected String firstName;
    protected String middleName;
    protected String lastName;

    public DriverBase(String firstName, String middleName, String lastName) {
        validateName(firstName, "Имя");
        validateName(middleName, "Отчество");
        validateName(lastName, "Фамилия");

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    // Общая валидация ФИО
    protected static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }
        if (!name.matches("[A-Za-zА-Яа-яЁё\\-]+")) {
            throw new IllegalArgumentException(fieldName + " содержит недопустимые символы");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    // Краткая версия
    public String toShortString() {
        return "DriverBase{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
