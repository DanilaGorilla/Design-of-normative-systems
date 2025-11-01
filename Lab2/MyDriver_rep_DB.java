package org.example;

import java.util.ArrayList;
import java.util.List;

public class MyDriver_rep_DB {

    private final DatabaseConnection db; // делегирование
    public MyDriver_rep_DB() {
        this.db = DatabaseConnection.getInstance();
    }

    public Driver getDriverById(int id) {
        String sql = "SELECT driver_id, first_name, middle_name, last_name, experience, payment FROM drivers WHERE driver_id = ?";
        List<Object[]> rows = db.executeQuery(sql, id);
        if (rows.isEmpty()) return null;

        Object[] r = rows.get(0);
        return new Driver(
                ((Number) r[0]).intValue(),
                (String) r[1],
                (String) r[2],
                (String) r[3],
                ((Number) r[4]).intValue(),
                ((Number) r[5]).doubleValue()
        );
    }

    public List<Driver> getDriverListPage(int k, int n) {
        String sql = "SELECT driver_id, first_name, middle_name, last_name, experience, payment FROM drivers ORDER BY driver_id LIMIT ? OFFSET ?";
        List<Object[]> rows = db.executeQuery(sql, k, (n - 1) * k);

        List<Driver> result = new ArrayList<>();
        for (Object[] r : rows) {
            result.add(new Driver(
                    ((Number) r[0]).intValue(),
                    (String) r[1],
                    (String) r[2],
                    (String) r[3],
                    ((Number) r[4]).intValue(),
                    ((Number) r[5]).doubleValue()
            ));
        }
        return result;
    }

    public void insertDriver(Driver driver) {
        String sql = "INSERT INTO drivers (first_name, middle_name, last_name, experience, payment) VALUES (?, ?, ?, ?, ?)";
        db.executeUpdate(sql, driver.getFirstName(), driver.getMiddleName(),
                driver.getLastName(), driver.getExperience(), driver.getPayment());
    }

    public boolean updateDriverById(int id, Driver newDriver) {
        String sql = "UPDATE drivers SET first_name=?, middle_name=?, last_name=?, experience=?, payment=? WHERE driver_id=?";
        int rows = db.executeUpdate(sql, newDriver.getFirstName(), newDriver.getMiddleName(),
                newDriver.getLastName(), newDriver.getExperience(), newDriver.getPayment(), id);
        return rows > 0;
    }

    public boolean deleteDriverById(int id) {
        String sql = "DELETE FROM drivers WHERE driver_id = ?";
        int rows = db.executeUpdate(sql, id);
        return rows > 0;
    }

    public int getDriverCount() {
        String sql = "SELECT COUNT(*) FROM drivers";
        List<Object[]> rows = db.executeQuery(sql);
        if (!rows.isEmpty()) {
            return ((Number) rows.get(0)[0]).intValue();
        }
        return 0;
    }
}
