package com.example.demo.service;

import com.example.demo.entity.ChucVu;

import java.util.List;
import java.util.Optional;

public interface GenericSevice<T,PK> {
    List<T> getAll();

    Optional<T> getOne(PK id);

    T add(T entity);

    T update(PK id);

}
