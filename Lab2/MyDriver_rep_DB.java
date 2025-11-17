package org.example;

import java.util.ArrayList;
import java.util.List;

public class MyDriver_rep_DB {

    private final DatabaseConnection db; // делегирование

    public MyDriver_rep_DB() {
        this.db = DatabaseConnection.getInstance();
    }

    public boolean isDriverExists(Driver driver) {
        String sql = "SELECT COUNT(*) FROM drivers WHERE first_name = ? AND middle_name = ? AND last_name = ? AND experience = ? AND payment = ? AND driver_id != ?";
        List<Object[]> rows = db.executeQuery(sql,
                driver.getFirstName(),
                driver.getMiddleName(),
                driver.getLastName(),
                driver.getExperience(),
                driver.getPayment(),
                driver.getDriverId()
        );
        return !rows.isEmpty() && ((Number) rows.get(0)[0]).intValue() > 0;
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
        Driver existingDriver = getDriverById(id);
        if (existingDriver == null) {
            System.out.println("Водитель с ID " + id + " не найден.");
            return false;
        }
        if (isDriverExists(newDriver)) {
            System.out.println("Ошибка: водитель с такими данными уже существует!");
            return false;
        }

        String sql = "UPDATE drivers SET first_name=?, middle_name=?, last_name=?, experience=?, payment=? WHERE driver_id=?";
        int rows = db.executeUpdate(sql, newDriver.getFirstName(), newDriver.getMiddleName(),
                newDriver.getLastName(), newDriver.getExperience(), newDriver.getPayment(), id);

        if (rows > 0) {
            System.out.println("Данные водителя успешно обновлены.");
            return true;
        } else {
            System.out.println("Не удалось обновить данные водителя.");
            return false;
        }
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
    //7 пункт
    public List<Driver> getDriverListPageWithFilterSort(int k, int n, String where, String order) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT driver_id, first_name, middle_name, last_name, experience, payment FROM drivers");

        if (where != null && !where.isEmpty()) {
            sql.append(" WHERE ").append(where);
        }

        if (order != null && !order.isEmpty()) {
            sql.append(" ORDER BY ").append(order);
        } else {
            sql.append(" ORDER BY driver_id");
        }

        sql.append(" LIMIT ").append(k).append(" OFFSET ").append((n - 1) * k);

        List<Object[]> rows = db.executeQuery(sql.toString());
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

    public int getDriverCountWithFilter(String where) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM drivers");
        if (where != null && !where.isEmpty()) {
            sql.append(" WHERE ").append(where);
        }
        List<Object[]> rows = db.executeQuery(sql.toString());
        return rows.isEmpty() ? 0 : ((Number) rows.get(0)[0]).intValue();
    }
}
