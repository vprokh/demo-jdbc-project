package com.example.jdbc.db;

import com.example.config.DbConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

// usually the connection pool is made as a singleton instance
public final class HikariPoolDataSource {

    private static HikariDataSource hikariDataSource;

    private HikariPoolDataSource() {
        // singleton
    }

    private static void initHikariConnectionPool() {
        DbConfig dbConfig = new DbConfig();
        HikariConfig config = new HikariConfig();

        // besides the setters way to set some properties, other approaches exists
        // 1. using Properties class
        // 2. passing properties file path to the constructor
        // which to use, depends on the specific project needs
        config.setJdbcUrl(dbConfig.getDbUrl());
        config.setUsername(dbConfig.getDbUser());
        config.setPassword(dbConfig.getDbPassword());
        config.setSchema("public");

        // might be moved to the properties file as well (probably, SHOULD be moved there)
        // take a look at the doc to get know what property does what - https://github.com/brettwooldridge/HikariCP#gear-configuration-knobs-baby
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        hikariDataSource = new HikariDataSource(config);
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (hikariDataSource == null) {
            initHikariConnectionPool();
        }

        return hikariDataSource.getConnection();
    }

    public static synchronized void shutdown() {
        if (hikariDataSource != null && !hikariDataSource.isClosed()) {
            // to release all connections -> shut down the pool
            hikariDataSource.close();
        }
    }
}
