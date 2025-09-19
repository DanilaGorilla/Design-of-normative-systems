package org.example;

import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Driver extends DriverBase {
    private int driverId;
    private int experience; // в годах
    private double payment;

    public Driver(int driverId, String firstName, String middleName, String lastName, int experience, double payment) {
        super(firstName, middleName, lastName); // используем конструктор родителя
        validateExperience(experience);
        validatePayment(payment);

        this.driverId = driverId;
        this.experience = experience;
        this.payment = payment;
    }

    // CSV
    public Driver(String csv) {
        super("", "", ""); // временно, потом перезапишем поля
        if (csv == null || csv.isBlank()) {
            throw new IllegalArgumentException("CSV строка не может быть пустой");
        }
        String[] parts = csv.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException(
                    "CSV строка должна содержать 6 полей: driverId, firstName, middleName, lastName, experience, payment");
        }

        this.driverId = Integer.parseInt(parts[0].trim());
        this.firstName = parts[1].trim();
        this.middleName = parts[2].trim();
        this.lastName = parts[3].trim();
        this.experience = Integer.parseInt(parts[4].trim());
        this.payment = Double.parseDouble(parts[5].trim());

        validateName(firstName, "Имя");
        validateName(middleName, "Отчество");
        validateName(lastName, "Фамилия");
        validateExperience(experience);
        validatePayment(payment);
    }

    // JSON
    public Driver(String jsonFilePath, boolean isJsonFile) throws IOException {
        super("", "", ""); // временно, потом перезапишем поля
        if (!isJsonFile) {
            throw new IllegalArgumentException("Для CSV используйте другой конструктор");
        }

        try (FileReader reader = new FileReader(jsonFilePath)) {
            Gson gson = new GsonBuilder().create();
            Driver temp = gson.fromJson(reader, Driver.class);

            validateName(temp.firstName, "Имя");
            validateName(temp.middleName, "Отчество");
            validateName(temp.lastName, "Фамилия");
            validateExperience(temp.experience);
            validatePayment(temp.payment);

            this.driverId = temp.driverId;
            this.firstName = temp.firstName;
            this.middleName = temp.middleName;
            this.lastName = temp.lastName;
            this.experience = temp.experience;
            this.payment = temp.payment;
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Ошибка в формате JSON", e);
        }
    }

    public int getDriverId() {
        return driverId;
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

    // Полная версия
    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driverId +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", experience=" + experience +
                ", payment=" + payment +
                '}';
    }

    // Сравнение
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver driver)) return false;
        return driverId == driver.driverId &&
                experience == driver.experience &&
                Double.compare(driver.payment, payment) == 0 &&
                Objects.equals(firstName, driver.firstName) &&
                Objects.equals(middleName, driver.middleName) &&
                Objects.equals(lastName, driver.lastName);
    }
}
