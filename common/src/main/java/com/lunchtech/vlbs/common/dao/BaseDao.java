package com.lunchtech.vlbs.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BaseDao<ID, V> {

    V get(ID id);

    void delete(ID id);

    List<V> findAll();

    int update(V v);
}