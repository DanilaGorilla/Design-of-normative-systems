package org.example;

public class Driver {
    private int driverId;
    private String firstName;
    private String middleName;
    private String lastName;
    private int experience; // в годах
    private double payment;

    public Driver(int driverId, String firstName, String middleName, String lastName, int experience, double payment) {
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
}
