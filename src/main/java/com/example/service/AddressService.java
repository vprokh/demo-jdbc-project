package com.example.service;

import com.example.dao.AddressDao;
import com.example.model.Address;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressService {
    private final AddressDao addressDao;

    public Address read(Long id) {
        return addressDao.read(id);
    }

    public void update(Long id, Address address) {
        addressDao.update(id, address);
    }

    public void delete(Long id) {
        addressDao.delete(id);
    }

    public void save(Address address) {
        addressDao.save(address);
    }
}
