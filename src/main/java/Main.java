import com.example.dao.AddressDao;
import com.example.dao.UserDao;
import com.example.db.DatabaseStorage;
import com.example.model.User;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        AddressDao addressDao = new AddressDao();
        UserDao userDao = new UserDao(addressDao);

        User user = userDao.readUser(1L);

        System.out.println(user);

        DatabaseStorage.getConnection().close();
    }
}
