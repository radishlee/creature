package com.lunchtech.vlbs.service.impl;

import com.lunchtech.vlbs.common.dao.BaseDao;
import com.lunchtech.vlbs.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * 请所有关于时间的字段，均以unix时间戳存入数据库中
 *
 * @param <ID>   id类型
 * @param <Bean>
 * @author zhoujie
 */
public class BaseServiceMysqlImpl<ID, Bean> implements BaseService<ID, Bean> {
    private BaseDao<ID, Bean> baseDao;

    @Override
    public Bean get(ID id) {
        return baseDao.get(id);
    }

    @Override
    public void delete(ID id) {
        baseDao.delete(id);
    }

    @Override
    public List<Bean> findAll() {
        return baseDao.findAll();
    }

    @Override
    public int update(Bean bean) {
        return baseDao.update(bean);
    }
}
