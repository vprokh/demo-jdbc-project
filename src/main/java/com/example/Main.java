package com.example;

import com.example.jdbc.dao.AddressDao;
import com.example.jdbc.dao.impl.AddressConnectionPoolDao;
import com.example.jdbc.dao.impl.AddressHikariConnectionPoolDao;
import com.example.jdbc.dao.impl.AddressSingletonConnectionDao;
import com.example.jdbc.dao.impl.UserDao;
import com.example.jdbc.db.DatabaseStorage;
import com.example.jdbc.db.HikariPoolDataSource;
import com.example.jdbc.db.SimpleConnectionPool;
import com.example.jdbc.model.User;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
//        useSingletonConnection();
        useHikariConnectionPool();
//        useConnectionPool();
    }

    private static void useHikariConnectionPool() {
        try {
            AddressDao addressHikariConnectionPoolDao = new AddressHikariConnectionPoolDao();
            UserDao userDao = new UserDao(addressHikariConnectionPoolDao);

            User user = userDao.read(1L);

            System.out.println(user);
        } finally {
            // do not forget to shut down connection pool after
            // its usage (usually you want to shut down it before program ends its execution)
            HikariPoolDataSource.shutdown();
        }
    }

    private static void useConnectionPool() throws SQLException {
        SimpleConnectionPool simpleConnectionPool = null;

        try {
            simpleConnectionPool = SimpleConnectionPool.create();
            AddressDao addressConnectionPoolDao = new AddressConnectionPoolDao(simpleConnectionPool);
            UserDao userDao = new UserDao(addressConnectionPoolDao);

            User user = userDao.read(1L);

            System.out.println(user);
        } finally {
            // do not forget to shut down connection pool after
            // its usage (usually you want to shut down it before program ends its execution)
            if (simpleConnectionPool != null) {
                simpleConnectionPool.shutdown();
            }
        }
    }

    private static void useSingletonConnection() throws SQLException {
        try {
            AddressDao addressSingletonConnectionDao = new AddressSingletonConnectionDao();
            UserDao userDao = new UserDao(addressSingletonConnectionDao);

            User user = userDao.read(1L);

            System.out.println(user);
        } finally {
            // do not forget to close connection after
            // its usage (usually you want to shut down it before program ends its execution)
            DatabaseStorage.getConnection().close();
        }
    }
}
