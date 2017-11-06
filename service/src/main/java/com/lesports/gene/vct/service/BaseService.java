package com.lesports.gene.vct.service;

import java.util.List;
import java.util.Map;

public interface BaseService<ID, V> {


    public V get(ID id);

    public void delete(ID id);

    public List<V> findAll();

    /**
     * 更新某个字段的数值，例如粉丝数、收藏数、评论数等等
     *
     * @param id
     * @param kv 字段名和对应的数值变化
     */
    public void updateFieldNum(ID id, Map<String, Integer> kv);

    /**
     * 简化版
     *
     * @param id
     * @param fieldName
     * @param num
     */
    public void updateFieldNum(ID id, String fieldName, int num);


    /**
     * 更新属性
     *
     * @param id
     * @param params
     * @return
     */
    public int update(ID id, Map<String, Object> params);
}