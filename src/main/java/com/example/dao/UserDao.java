package com.example.dao;

import com.example.db.DatabaseStorageSingleton;
import com.example.model.Role;
import com.example.model.User;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class UserDao {

    private static final String GET_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT_USER_PREPARED_STATEMENT =
            "INSERT INTO users(\"name\", \"last_name\", \"email\", \"phone_number\", \"password\", \"role\", \"address_id\") VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_PREPARED_STATEMENT =
            "UPDATE users SET name = ?, last_name = ?, email = ?, phone_number = ?, password = ?, role = ?, address_id = ? WHERE id = ?";
    private static final String DELETE_USER_PREPARED_STATEMENT = "DELETE FROM users WHERE id = ?";

    private final AddressConnectionPoolDao addressConnectionPoolDao;

    public User readUser(Long id) {
        try {
            Connection connection = DatabaseStorageSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user = new User();

            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setPassoword(resultSet.getString("password"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                user.setAddress(addressConnectionPoolDao.readAddress(resultSet.getLong("address_id")));
            }

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUser(User user) {
        try {
            Connection connection = DatabaseStorageSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_PREPARED_STATEMENT);
            connection.setAutoCommit(false);

            populatePreparedStatement(user, preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateUser(Long id, User user) {
        try {
            Connection connection = DatabaseStorageSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_PREPARED_STATEMENT);
            populatePreparedStatement(user, preparedStatement);
            preparedStatement.setLong(8, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(Long id) {
        try {
            Connection connection = DatabaseStorageSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_PREPARED_STATEMENT);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void populatePreparedStatement(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPhoneNumber());
        preparedStatement.setString(5, user.getPassoword());
        preparedStatement.setString(6, user.getRole().name());
        preparedStatement.setLong(7, user.getAddress().getId());
    }
}
