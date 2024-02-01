package com.example.config;

import com.example.hibernate.model.MyUser;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Getter
public final class HibernateConfig {
    private static final HibernateConfig INSTANCE;

    private final SessionFactory sessionFactory;

    static {
        INSTANCE = new HibernateConfig();
    }

    private HibernateConfig() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(MyUser.class)
                .buildSessionFactory();
    }

    public static HibernateConfig getInstance() {
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }

}
