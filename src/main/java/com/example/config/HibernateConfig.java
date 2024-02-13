package com.example.config;

import com.example.hibernate.model.MyUser;
import com.example.hibernate.model.MyUserCombinedPk;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
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
                .addAnnotatedClass(MyUserCombinedPk.class)
                // TODO: additional config or any config from the property file can be set here as well
                .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.format_sql", "true")
//                .addAttributeConverter(new BirthDateConverter())
//                none - No action is performed. The schema will not be generated.
//                create-only - The database schema will be generated.
//                drop - The database schema will be dropped.
//                create - The database schema will be dropped and created afterward.
//                create-drop - The database schema will be dropped and created afterward. Upon closing the SessionFactory, the database schema will be dropped.
//                validate - The database schema will be validated using the entity mappings.
//                update - The database schema will be updated by comparing the existing database schema with the entity mappings.
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .buildSessionFactory();
    }

    public static HibernateConfig getInstance() {
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }

}
