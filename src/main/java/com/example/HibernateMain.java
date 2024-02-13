package com.example;

import com.example.config.HibernateConfig;
import com.example.hibernate.model.BirthDate;
import com.example.hibernate.model.CombinedMyUserPk;
import com.example.hibernate.model.MyUser;
import com.example.hibernate.model.MyUserCombinedPk;
import com.example.jdbc.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

public class HibernateMain {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        HibernateConfig hibernateConfig = HibernateConfig.getInstance();

        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            MyUser user = createUser();
            MyUserCombinedPk combinedPk = createMyUserCombinedPk();

//            session.persist(user);
            session.save(user);

            // read
//            MyUser myUser = session.find(MyUser.class, 1L);
//            System.out.println(myUser);
//            session.get(MyUser.class, 1L);

            // update
//            session.saveOrUpdate(user);
//            session.update(user);
//            session.flush();

            user.setName("demo-name");

            session.refresh(user);
            MyUser myUser = session.merge(user);
            user.setName("demo-name-1");
            session.flush();

            // delete
//            session.remove(user);
//            session.delete(user);

            transaction.commit();
        } finally {
            hibernateConfig.close();
        }

        System.out.println("done!");
    }

    private static void howHibernateUsesReflectionAndAnnotations() throws SQLException, IllegalAccessException {
        Object user = new MyUser();

        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;

        String tableName = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name()) // public.my_users
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();

        String columnNames = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(", ")); // first_name, last_name, email, ...

        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(joining(", ")); // ?, ?, ?, ?

        System.out.println(sql.formatted(tableName, columnNames, columnValues));

        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            preparedStatement.setObject(1, declaredField.get(user));
        }
    }

    private static void persistUserExample() {
        HibernateConfig hibernateConfig = HibernateConfig.getInstance();

        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            MyUser user = createUser();
            MyUserCombinedPk combinedPk = createMyUserCombinedPk();

            session.persist(user);
            session.persist(combinedPk);

            transaction.commit();
        } finally {
            hibernateConfig.close();
        }

        System.out.println("done!");
    }

    private static MyUser createUser() {
        MyUser user = new MyUser();
        user.setEmail("tes@test1.com");
        user.setPhoneNumber("phon11e");
        user.setPassword("pass");
        user.setBirthDate(new BirthDate());
        return user;
    }

    private static MyUserCombinedPk createMyUserCombinedPk() {
        MyUserCombinedPk user = new MyUserCombinedPk();
        user.setCombinedPk(new CombinedMyUserPk());
        user.setEmail("tes@test1.com");
        user.setPhoneNumber("phon11e");
        user.setPassword("pass");
        user.setBirthDate(new BirthDate());
        return user;
    }

    private static Address createAddress() {
        Address address = new Address();

        address.setDisplayAddress("Hibernate demo!");

        return address;
    }
}
