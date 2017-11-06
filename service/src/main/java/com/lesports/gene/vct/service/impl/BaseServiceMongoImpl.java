package com.lesports.gene.vct.service.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.lesports.gene.vct.service.BaseService;
import com.mongodb.WriteResult;

/**
 * 请所有关于时间的字段，均以unix时间戳存入数据库中
 * @author zhoujie
 * @param <ID> id类型
 * @param <Bean>
 */
public class BaseServiceMongoImpl<ID, Bean> implements BaseService<ID, Bean> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected MongoTemplate mongoTemplate;
	private Class<Bean> entityClass;
	@SuppressWarnings("unused")
	private Class<ID> key;
	@SuppressWarnings("unchecked")
	public BaseServiceMongoImpl() {
		Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
		key = (Class<ID>)types[0];
		entityClass = (Class<Bean>)types[1];
    }
	@Override
	public void delete(ID id) {
		mongoTemplate.remove(query(where("_id").is(id)), entityClass);
	}
	@Override
	public List<Bean> findAll() {
		return mongoTemplate.findAll(entityClass);
	}
	
	public Bean insert(Bean m) {
		this.mongoTemplate.insert(m);
		return m;
	}
	@Override
	public Bean get(ID id) {
		return mongoTemplate.findById(id, entityClass);
	}

	@Override
	public void updateFieldNum(ID id, Map<String, Integer> kv) {
		if (kv == null) {
			return;
		}
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update();
		Set<Entry<String, Integer>> values = kv.entrySet();
		for (Entry<String, Integer> v : values) {
			String fieldName = v.getKey();
			Integer num = v.getValue();
			update.inc(fieldName, num);
		}
		this.mongoTemplate.updateFirst(query, update, entityClass);
	}
	
	@Override
	public void updateFieldNum(ID id, String fieldName, int num) {
		//TODO 如果后期数据量大后，可以把这里改成一个队列服务，将任务发到队列中即可
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().inc(fieldName, num);
		this.mongoTemplate.updateFirst(query, update, entityClass);
	}
	
	
	
	@Override
	public int update(ID id, Map<String, Object> params) {
		if (params.isEmpty()) {
			return 0;
		}
		Set<String> param = params.keySet();
		Update u = new Update();
		for (String p : param) {
			Object o = params.get(p);
			if ((o instanceof String) && (!"".equals(o))) {
				u.set(p, params.get(p));
			} else {
				u.set(p, params.get(p));
			}
		}
		WriteResult wr = mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").is(id)), u, entityClass);
		return wr.getN();
	}
	
}
