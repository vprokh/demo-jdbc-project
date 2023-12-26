import com.example.dao.AddressConnectionPoolDao;
import com.example.dao.AddressSingletonConnectionDao;
import com.example.dao.UserDao;
import com.example.db.DatabaseStorage;
import com.example.db.SimpleConnectionPool;
import com.example.model.User;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, InterruptedException {
//        useSingletonConnection();
        useConnectionPool();
    }

    private static void useConnectionPool() throws SQLException {
        SimpleConnectionPool simpleConnectionPool = null;

        try {
            simpleConnectionPool = SimpleConnectionPool.create();
            AddressConnectionPoolDao addressConnectionPoolDao = new AddressConnectionPoolDao(simpleConnectionPool);
            UserDao userDao = new UserDao(addressConnectionPoolDao);

            User user = userDao.readUser(1L);

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
            AddressSingletonConnectionDao addressSingletonConnectionDao = new AddressSingletonConnectionDao();
            UserDao userDao = new UserDao(addressSingletonConnectionDao);

            User user = userDao.readUser(1L);

            System.out.println(user);
        } finally {
            // do not forget to close connection after
            // its usage (usually you want to shut down it before program ends its execution)
            DatabaseStorage.getConnection().close();
        }
    }
}
