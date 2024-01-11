package com.example.db;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleConnectionPool {

    private final List<Connection> connectionPool;
    private List<Connection> usedConnections = new CopyOnWriteArrayList<>();

    private static int INITIAL_POOL_SIZE = 10;

    public static SimpleConnectionPool create() throws SQLException {
        List<Connection> pool = new CopyOnWriteArrayList<>();
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(DatabaseStorage.getConnection());
        }

        return new SimpleConnectionPool(pool);
    }

    public Connection getConnection() {
        Connection connection = null;

        connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);

        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        if (connection == null) {
            return false;
        }

        connectionPool.add(connection);

        return usedConnections.remove(connection);
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    public void shutdown() {
        try {
            usedConnections.forEach(this::releaseConnection);
            for (Connection connection : connectionPool) {
                connection.close();
            }
            connectionPool.clear();

        } catch (SQLException e) {
            System.out.println("LOG it!");
        }
    }
}
