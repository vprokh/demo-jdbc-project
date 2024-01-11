package com.example.dao.impl;

import com.example.db.DatabaseStorageSingleton;
import com.example.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
public class AddressSingletonConnectionDaoTest {

    private static final String INSERT_ADDRESS_STATEMENT =
            "INSERT INTO address(id, display_address,post_code,city,street) VALUES (%s, '%s', '%s', '%s', '%s')";

    private static final String MOCK_VALUE_FROM_DB = "mock value from DB";
    private static final Timestamp MOCK_CREATED_AT_TIMESTAMP = new Timestamp(1704983313L);
    private static final LocalDateTime MOCK_CREATED_AT = MOCK_CREATED_AT_TIMESTAMP.toLocalDateTime();
    private static final long MOCK_ADDRESS_ID = 1L;
    public static final String MOCK_POST_CODE_VALUE = "mock post code value";

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;

    private AddressSingletonConnectionDao addressSingletonConnectionDao;

    @BeforeEach
    void setUp() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getLong(anyString())).thenReturn(MOCK_ADDRESS_ID);
        when(resultSet.getString(anyString())).thenReturn(MOCK_VALUE_FROM_DB);
        when(resultSet.getTimestamp(anyString())).thenReturn(MOCK_CREATED_AT_TIMESTAMP);

        addressSingletonConnectionDao = new AddressSingletonConnectionDao();
    }

    @Test
    void readAddressShouldReturnExpectedAddressWhenIdPassed() {
        try (MockedStatic<DatabaseStorageSingleton> mockedStatic = Mockito.mockStatic(DatabaseStorageSingleton.class)) {
            mockedStatic.when(DatabaseStorageSingleton::getConnection).thenReturn(connection);
            Address address = addressSingletonConnectionDao.read(1L);

            assertEquals(MOCK_ADDRESS_ID, address.getId());
            assertEquals(MOCK_VALUE_FROM_DB, address.getDisplayAddress());
            assertEquals(MOCK_VALUE_FROM_DB, address.getStreet());
            assertEquals(MOCK_VALUE_FROM_DB, address.getCity());
            assertEquals(MOCK_VALUE_FROM_DB, address.getPostCode());
            assertEquals(MOCK_CREATED_AT, address.getCreatedAt());
        }
    }

    @Test
    void saveAddressesShouldExecuteExpectedPrepareStatementQuery() throws SQLException {
        try (MockedStatic<DatabaseStorageSingleton> mockedStatic = mockStatic(DatabaseStorageSingleton.class)) {
            mockedStatic.when(DatabaseStorageSingleton::getConnection).thenReturn(connection);
            Address address = createAddress();

            addressSingletonConnectionDao.savePreparedStatement(Collections.singletonList(address));

            verify(preparedStatement, never()).setLong(1, address.getId());
            verify(preparedStatement).setString(1, address.getDisplayAddress());
            verify(preparedStatement).setString(2, address.getPostCode());
            verify(preparedStatement).setString(3, address.getCity());
            verify(preparedStatement).setString(4, address.getStreet());
            verify(preparedStatement).setTimestamp(anyInt(), any(Timestamp.class));
        }
    }

    @Test
    void saveAddressesShouldExecuteExpectedStatementsInBatchQuery() throws SQLException {
        try (MockedStatic<DatabaseStorageSingleton> mockedStatic = mockStatic(DatabaseStorageSingleton.class)) {
            Address address = createAddress();
            final String expectedQuery = String.format(INSERT_ADDRESS_STATEMENT,
                    address.getId(), address.getDisplayAddress(), address.getPostCode(), address.getCity(), address.getStreet());

            when(connection.createStatement()).thenReturn(statement);
            mockedStatic.when(DatabaseStorageSingleton::getConnection).thenReturn(connection);

            addressSingletonConnectionDao.save(Collections.singletonList(address));

            verify(statement).addBatch(expectedQuery);
            verify(statement).executeBatch();
        }
    }

    private Address createAddress() {
        Address address = new Address();

        address.setId(MOCK_ADDRESS_ID);
        address.setDisplayAddress(MOCK_VALUE_FROM_DB);
        address.setPostCode(MOCK_POST_CODE_VALUE);

        return address;
    }
}
