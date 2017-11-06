package com.lunchtech.vlbs.service;

import java.util.List;

public interface BaseService<ID, V> {

    V get(ID id);

    void delete(ID id);

    List<V> findAll();

    int update(V v);
}