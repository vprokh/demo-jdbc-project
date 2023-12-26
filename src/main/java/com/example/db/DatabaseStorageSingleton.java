package com.example.db;

import com.example.config.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public final class DatabaseStorageSingleton {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/GoItDemo";
    private static final String USERNAME = "user-name";
    private static final String PASSWORD = "strong-password";

    private static Connection connection;

    private DatabaseStorageSingleton() {
        // singleton
    }

    public static Connection getConnection() throws SQLException {
        DbConfig dbConfig = new DbConfig();
        if (Objects.isNull(connection)) {
            connection = DriverManager.getConnection(DB_URL, dbConfig.getDbProperties());
        }

        if (!connection.isValid(1000)) {
            connection = DriverManager.getConnection(DB_URL, dbConfig.getDbProperties());
        }

        return connection;
    }
}
