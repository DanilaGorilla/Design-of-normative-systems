package org.example;

public class DriverBase {

    protected String firstName;
    protected String middleName;
    protected String lastName;

    public DriverBase(String firstName, String middleName, String lastName) {
        Driver.validateName(firstName, "Имя");
        Driver.validateName(middleName, "Отчество");
        Driver.validateName(lastName, "Фамилия");

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
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
    @Override
    public String toString() {
        return "DriverBase{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
