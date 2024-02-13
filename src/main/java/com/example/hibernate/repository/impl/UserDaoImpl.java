package com.example.hibernate.repository.impl;

import com.example.config.HibernateConfig;
import com.example.hibernate.repository.UserDao;
import com.example.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl() {
        this.sessionFactory = HibernateConfig.getInstance().getSessionFactory();
    }

    @Override
    public User findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public void create(User entity) {
        try (Session session = sessionFactory.openSession()) {
            session.persist(entity);
        }
    }

    @Override
    public void delete(User entity) {
        try (Session session = sessionFactory.openSession()) {
            session.remove(entity);
        }
    }

    @Override
    public User update(User entity) {
        try (Session session = sessionFactory.openSession()) {
            return session.merge(entity);
        }
    }
}
