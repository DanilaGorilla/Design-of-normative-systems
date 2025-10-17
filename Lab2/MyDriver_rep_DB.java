package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyDriver_rep_DB extends MyDriver_rep_base {

    private final String url = "jdbc:postgresql://localhost:5432/drivers_db";
    private final String user = "postgres";
    private final String password = "postpass";

    public MyDriver_rep_DB(String filePath) {
        super(filePath);
    }
    // Подключение к базе данных
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    @Override
    public void readAll() {
    }
    @Override
    public void writeAll() {
    }
    // a. Получить объект по ID
    public Driver getById(int id) {
        String sql = "SELECT * FROM drivers WHERE driver_id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getString("last_name"),
                        rs.getInt("experience"),
                        rs.getDouble("payment")
                );
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при получении данных: " + e.getMessage());
        }
        return null;
    }
    // b. Получить список k по счёту n объектов (постранично)
    public List<Driver> get_k_n_short_list(int k, int n) {
        List<Driver> result = new ArrayList<>();
        String sql = "SELECT * FROM drivers ORDER BY driver_id LIMIT ? OFFSET ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, k);
            stmt.setInt(2, (n - 1) * k);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getString("last_name"),
                        rs.getInt("experience"),
                        rs.getDouble("payment")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка: " + e.getMessage());
        }

        return result;
    }

    // c. Добавить объект в таблицу (с авто-ID)
    public void addDriver(Driver driver) {
        String sql = "INSERT INTO drivers (first_name, middle_name, last_name, experience, payment) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, driver.getFirstName());
            stmt.setString(2, driver.getMiddleName());
            stmt.setString(3, driver.getLastName());
            stmt.setInt(4, driver.getExperience());
            stmt.setDouble(5, driver.getPayment());

            stmt.executeUpdate();
            System.out.println("Водитель успешно добавлен в базу данных.");

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении водителя: " + e.getMessage());
        }
    }
    // d. Заменить элемент по ID
    public boolean replaceById(int id, Driver newDriver) {
        String sql = "UPDATE drivers SET first_name=?, middle_name=?, last_name=?, experience=?, payment=? WHERE driver_id=?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newDriver.getFirstName());
            stmt.setString(2, newDriver.getMiddleName());
            stmt.setString(3, newDriver.getLastName());
            stmt.setInt(4, newDriver.getExperience());
            stmt.setDouble(5, newDriver.getPayment());
            stmt.setInt(6, id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении водителя: " + e.getMessage());
        }
        return false;
    }
    // e. Удалить элемент по ID
    public boolean deleteById(int id) {
        String sql = "DELETE FROM drivers WHERE driver_id = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении водителя: " + e.getMessage());
        }
        return false;
    }
    // f. Получить количество элементов
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM drivers";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при получении количества: " + e.getMessage());
        }
        return 0;
    }
}
