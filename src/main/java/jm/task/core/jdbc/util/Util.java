package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mysqltest?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME = "Daniil210";
    private static final String PASSWORD = "rootik";

    public static final Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }
}
