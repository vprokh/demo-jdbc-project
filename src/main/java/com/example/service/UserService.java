package com.example.service;

import com.example.dao.impl.UserDao;
import com.example.model.User;
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
