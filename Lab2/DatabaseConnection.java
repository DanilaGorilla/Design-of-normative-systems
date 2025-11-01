package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static DatabaseConnection instance; // единственный экземпляр
    private final String url = "jdbc:postgresql://localhost:5432/drivers_db";
    private final String user = "postgres";
    private final String password = "postpass";

    private DatabaseConnection() {
        //конструктор(никто не может создать объект извне)
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер PostgreSQL не найден: " + e.getMessage());
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    //SELECT
    public List<Object[]> executeQuery(String sql, Object... params) {
        List<Object[]> result = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();

            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                result.add(row);
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
        }
        return result;
    }

    //INSERT/UPDATE/DELETE
    public int executeUpdate(String sql, Object... params) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);
            return stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка при изменении данных: " + e.getMessage());
        }
        return 0;
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }
}
