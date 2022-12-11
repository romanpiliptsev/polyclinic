package com.rschir.polyclinic.dbservice.dao.interfaces;

import java.util.List;

public interface Dao<T> {
    void save(T object);
    T getById(Long id);
    List<T> getAll();
    void remove(Long id);
}
