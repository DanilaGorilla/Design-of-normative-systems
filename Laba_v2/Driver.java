package org.example;

import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.IOException;

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

    //CSV
    public Driver(String csv) {
        if (csv == null || csv.isBlank()) {
            throw new IllegalArgumentException("CSV строка не может быть пустой");
        }
        String[] parts = csv.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException(
                    "CSV строка должна содержать 6 полей: driverId, firstName, middleName, lastName, experience, payment");
        }

        int parsedDriverId = Integer.parseInt(parts[0].trim());
        String parsedFirstName = parts[1].trim();
        String parsedMiddleName = parts[2].trim();
        String parsedLastName = parts[3].trim();
        int parsedExperience = Integer.parseInt(parts[4].trim());
        double parsedPayment = Double.parseDouble(parts[5].trim());

        validateName(parsedFirstName, "Имя");
        validateName(parsedMiddleName, "Отчество");
        validateName(parsedLastName, "Фамилия");
        validateExperience(parsedExperience);
        validatePayment(parsedPayment);

        this.driverId = parsedDriverId;
        this.firstName = parsedFirstName;
        this.middleName = parsedMiddleName;
        this.lastName = parsedLastName;
        this.experience = parsedExperience;
        this.payment = parsedPayment;
    }

    //JSON
    public Driver(String jsonFilePath, boolean isJsonFile) throws IOException {
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
