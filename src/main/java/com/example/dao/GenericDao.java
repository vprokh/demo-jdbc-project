package com.example.dao;

import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public interface GenericDao<T, K> {
    T read(K id);

    void save(T entity);

    default void save(List<T> entities) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("This method is not implemented yet");
    }

    void delete(K id);

    void update(K id, T updatedEntity);
}
