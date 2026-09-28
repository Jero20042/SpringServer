package org.example.sqlOperations;

import java.sql.*;


public class SqlConnection {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String DB_PASSWORD = "123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, DB_PASSWORD);
    }


    protected ResultSet sendQuery(String sqlQuery) throws SQLException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(sqlQuery);
            ResultSet result = statement.executeQuery();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected void sendUpdate(String sqlQuery) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            System.out.println("Записей добавлено:"+statement.executeUpdate());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void sendDelete(String sqlQuery) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            System.out.println("Записей удалено:"+statement.executeUpdate());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}