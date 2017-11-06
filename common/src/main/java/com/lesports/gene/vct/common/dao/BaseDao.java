package com.lesports.gene.vct.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BaseDao<ID, V> {

    V get(ID id);

    void delete(ID id);

    List<V> findAll();

    int update(V v);
}