package org.example;

import java.util.ArrayList;
import java.util.List;

public class MyDriver_rep_DB extends MyDriver_rep_base {

    private final DatabaseConnection db; // делегирование

    public MyDriver_rep_DB(String filePath) {
        super(filePath);
        this.db = DatabaseConnection.getInstance(); // получаем единственный экземпляр
    }

    @Override
    public void readAll() {
    }
    @Override
    public void writeAll() {
    }

    public Driver getById(int id) {
        String sql = "SELECT * FROM drivers WHERE driver_id = ?";
        List<Object[]> rows = db.executeQuery(sql, id);

        if (rows.isEmpty()) return null;
        Object[] r = rows.get(0);

        return new Driver(
                (int) r[0],
                (String) r[1],
                (String) r[2],
                (String) r[3],
                (int) r[4],
                (double) r[5]
        );
    }

    public List<Driver> get_k_n_short_list(int k, int n) {
        String sql = "SELECT * FROM drivers ORDER BY driver_id LIMIT ? OFFSET ?";
        List<Object[]> rows = db.executeQuery(sql, k, (n - 1) * k);
        List<Driver> result = new ArrayList<>();

        for (Object[] r : rows) {
            result.add(new Driver(
                    (int) r[0],
                    (String) r[1],
                    (String) r[2],
                    (String) r[3],
                    (int) r[4],
                    (double) r[5]
            ));
        }

        return result;
    }

    public void addDriver(Driver driver) {
        String sql = "INSERT INTO drivers (first_name, middle_name, last_name, experience, payment) VALUES (?, ?, ?, ?, ?)";
        db.executeUpdate(sql,
                driver.getFirstName(),
                driver.getMiddleName(),
                driver.getLastName(),
                driver.getExperience(),
                driver.getPayment());
    }

    public boolean replaceById(int id, Driver newDriver) {
        String sql = "UPDATE drivers SET first_name=?, middle_name=?, last_name=?, experience=?, payment=? WHERE driver_id=?";
        int rows = db.executeUpdate(sql,
                newDriver.getFirstName(),
                newDriver.getMiddleName(),
                newDriver.getLastName(),
                newDriver.getExperience(),
                newDriver.getPayment(),
                id);
        return rows > 0;
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM drivers WHERE driver_id = ?";
        int rows = db.executeUpdate(sql, id);
        return rows > 0;
    }

    public int getCount() {
        String sql = "SELECT COUNT(*) FROM drivers";
        List<Object[]> rows = db.executeQuery(sql);
        if (!rows.isEmpty()) {
            return ((Number) rows.get(0)[0]).intValue();
        }
        return 0;
    }
}
