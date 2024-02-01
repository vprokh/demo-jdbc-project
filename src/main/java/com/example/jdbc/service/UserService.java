package com.example.jdbc.service;

import com.example.jdbc.dao.impl.UserDao;
import com.example.jdbc.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User read(Long id) {
        return userDao.read(id);
    }

    public void update(Long id, User user) {
        userDao.update(id, user);
    }

    public void delete(Long id) {
        userDao.delete(id);
    }

    public void save(User user) {
        userDao.save(user);
    }

}
