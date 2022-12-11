package com.rschir.polyclinic.dbservice.services.interfaces;

import java.util.List;

public interface Service<T> {
    void save(T object);
    T getById(Long id);
    List<T> getAll();
    void remove(Long id);
}
