package com.example.db;

import com.example.config.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseStorage {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/GoItDemo";

    public static Connection getConnection() throws SQLException {
        DbConfig dbConfig = new DbConfig();
        return DriverManager.getConnection(DB_URL, dbConfig.getDbProperties());
    }
}
