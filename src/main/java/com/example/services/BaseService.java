package com.example.services;

import java.util.List;

public interface BaseService<T> {
    T findById (Long id);
    List<T> findAll();

    void add (T model);
    void update (T model);
    void delete (T model);
    void delete (Long id);
}
