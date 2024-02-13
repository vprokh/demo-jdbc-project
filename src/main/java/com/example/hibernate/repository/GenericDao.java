package com.example.hibernate.repository;

import java.io.Serializable;


public interface GenericDao<V, T extends Serializable> {

    V findById(T id);

    void create(V entity);

    void delete(V entity);

    V update(V entity);
}
