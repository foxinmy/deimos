package com.foxinmy.deimos.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import com.foxinmy.deimos.dao.BaseDao;
import com.foxinmy.jycore.util.ReflectionUtil;

/**
 * 数据访问基类实现类
 * @author jy.hu
 *
 * @param <T> 类
 * @param <PK> 主键
 */
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

	@Autowired
	private MongoOperations mongoOperations;

	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Class<?> c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	@Override
	public T findOne(PK id) {
		Assert.notNull(id, "entitys is required");
		return mongoOperations.findOne(new Query(Criteria.where("id").is(id)),
				entityClass);
	}

	@Override
	public T findOne(Criteria criteria) {
		return mongoOperations.findOne(new Query(criteria), entityClass);
	}

	@Override
	public List<T> findAll() {
		return mongoOperations.findAll(entityClass);
	}

	@Override
	public Long getTotalCount() {
		return mongoOperations.count(new Query(), entityClass);
	}

	@Override
	public void save(T entity) {
		Assert.notNull(entity, "entity is required");
		mongoOperations.insert(entity);
	}

	@Override
	public void save(List<T> entitys) {
		Assert.notNull(entitys, "entitys is required");
		mongoOperations.insertAll(entitys);
	}

	@Override
	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		Update update = new Update();
		Field[] fileds = entityClass.getFields();
		for (int i = 0; i < fileds.length; i++) {
			update.set(fileds[0].getName(),
					ReflectionUtil.getFieldValue(entity, fileds[0].getName()));
		}
		mongoOperations.updateFirst(
				new Query(Criteria.where("id").is(
						ReflectionUtil.getFieldValue(entity, "id"))), update,
				entityClass);
	}

	@Override
	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		T t = findOne(id);
		if (t != null)
			mongoOperations.remove(t);
	}

	@Override
	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		mongoOperations.remove(entity);
	}

	@Override
	public void delete(PK[] ids) {
		Assert.notNull(ids, "ids is required");
		for (PK id : ids) {
			delete(id);
		}
	}
}
