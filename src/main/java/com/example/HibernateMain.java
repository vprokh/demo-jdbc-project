package com.example;

import com.example.config.HibernateConfig;
import com.example.jdbc.model.Address;
import com.example.hibernate.model.MyUser;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateMain {
    public static void main(String[] args) {
        HibernateConfig hibernateConfig = HibernateConfig.getInstance();

        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            MyUser user = createUser();

            session.persist(user);

            transaction.commit();
        } finally {
            hibernateConfig.close();
        }

    }

    private static MyUser createUser() {
        MyUser user = new MyUser();
        user.setEmail("tes@test1.com");
        user.setPhoneNumber("phon1e");
        user.setPassword("pass");
        return user;
    }

    private static Address createAddress() {
        Address address = new Address();

        address.setDisplayAddress("Hibernate demo!");

        return address;
    }
}
