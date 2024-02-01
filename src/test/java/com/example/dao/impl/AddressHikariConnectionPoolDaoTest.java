package com.example.dao.impl;


import com.example.jdbc.dao.impl.AddressHikariConnectionPoolDao;
import com.example.jdbc.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressHikariConnectionPoolDaoTest {

    private static final Long DB_ADDRESS_ID = 1L;
    private static final Long DB_SAVED_ADDRESS_ID = 4L;

    private AddressHikariConnectionPoolDao addressHikariConnectionPoolDao;

    @BeforeEach
    void setUp() {
        addressHikariConnectionPoolDao = new AddressHikariConnectionPoolDao();
    }

    @Test
    void readAddressShouldReturnExpectedAddress() {
        Address address = addressHikariConnectionPoolDao.read(DB_ADDRESS_ID);

        assertEquals(DB_ADDRESS_ID, address.getId());
        assertEquals("123 com.example.Main St", address.getDisplayAddress());
        assertEquals("com.example.Main Street", address.getStreet());
        assertEquals("New York", address.getCity());
        assertEquals("ABC123", address.getPostCode());
    }

    @Test
    void saveAddressShouldSaveExpectedAddressToDb() {
        Address address = createAddress();

        addressHikariConnectionPoolDao.save(address);
        Address actualAddress = addressHikariConnectionPoolDao.read(DB_SAVED_ADDRESS_ID);

        assertEquals(address, actualAddress);
    }


    private Address createAddress() {
        Address address = new Address();

        address.setId(DB_SAVED_ADDRESS_ID);
        address.setDisplayAddress("display name inserted");
        address.setPostCode("poscode name inserted");

        return address;
    }
}
