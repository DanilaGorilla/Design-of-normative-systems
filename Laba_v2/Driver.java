package org.example;

public class Driver {
    private int driverId; 
    private String firstName;
    private String middleName;
    private String lastName;
    private int experience; // в годах
    private double payment;
    
    public Driver(int driverId, String firstName, String middleName, String lastName, int experience, double payment) {
        validateName(firstName, "Имя");
        validateName(middleName, "Отчество");
        validateName(lastName, "Фамилия");
        validateExperience(experience);
        validatePayment(payment);

        this.driverId = driverId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.experience = experience;
        this.payment = payment;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
    public static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }
        if (!name.matches("[A-Za-zА-Яа-яЁё\\-]+")) {
            throw new IllegalArgumentException(fieldName + " содержит недопустимые символы");
        }
    }

    public static void validateExperience(int experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("Опыт работы не может быть отрицательным");
        }
    }

    public static void validatePayment(double payment) {
        if (payment < 0) {
            throw new IllegalArgumentException("Оплата не может быть отрицательной");
        }
    }
}
