package com.example.dao.impl;

import com.example.dao.AddressDao;
import com.example.db.HikariPoolDataSource;
import com.example.model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

// CRUD -> Create, Read, Update, Delete
// DAO -> Data access object
public class AddressHikariConnectionPoolDao implements AddressDao {
    private static final String GET_ADDRESS_QUERY =
            "SELECT * FROM address WHERE id = ?";
    private static final String INSERT_ADDRESS_PREPARED_STATEMENT =
            "INSERT INTO address(display_address,post_code,city,street, created_at) VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_ADDRESS_STATEMENT =
            "INSERT INTO address(id, display_address,post_code,city,street) VALUES (%s, '%s', '%s', '%s', '%s')";
    private static final String UPDATE_ADDRESS_PREPARED_STATEMENT =
            "UPDATE address SET display_address = ?, city = ?, post_code = ?, street = ?, created_at = ? WHERE id = ?";
    private static final String DELETE_ADDRESS_PREPARED_STATEMENT = "DELETE FROM address WHERE id = ?";

    @Override
    public Address read(Long id) {
        try (Connection connection = HikariPoolDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS_QUERY);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Address address = new Address();

            while (resultSet.next()) {
                address.setId(resultSet.getLong("id"));
                address.setDisplayAddress(resultSet.getString("display_address"));
                address.setPostCode(resultSet.getString("post_code"));
                address.setCity(resultSet.getString("city"));
                address.setStreet(resultSet.getString("street"));
                address.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }

            return address;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Address address) {
        try (Connection connection = HikariPoolDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADDRESS_PREPARED_STATEMENT);
            populatePrepareStatement(preparedStatement, address);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(List<Address> addresses) {
        try (Connection connection = HikariPoolDataSource.getConnection()) {
            Statement statement = connection.createStatement();

            for (Address address : addresses) {
                String sql = String.format(INSERT_ADDRESS_STATEMENT,
                        address.getId(), address.getDisplayAddress(), address.getPostCode(), address.getCity(), address.getStreet());
                statement.addBatch(sql);
            }

            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePreparedStatement(List<Address> addresses) {
        try (Connection connection = HikariPoolDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADDRESS_PREPARED_STATEMENT);

            for (Address address : addresses) {
                populatePrepareStatement(preparedStatement, address);
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Address address) {
        try (Connection connection = HikariPoolDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS_PREPARED_STATEMENT);

            populatePrepareStatement(preparedStatement, address);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(address.getCreatedAt()));
            preparedStatement.setLong(6, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = HikariPoolDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADDRESS_PREPARED_STATEMENT);

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void populatePrepareStatement(PreparedStatement preparedStatement, Address address) throws SQLException {
        preparedStatement.setString(1, address.getDisplayAddress());
        preparedStatement.setString(2, address.getPostCode());
        preparedStatement.setString(3, address.getCity());
        preparedStatement.setString(4, address.getStreet());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(address.getCreatedAt()));
    }
}
