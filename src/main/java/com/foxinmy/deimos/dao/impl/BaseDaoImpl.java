package com.foxinmy.deimos.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.foxinmy.deimos.dao.BaseDao;
import com.foxinmy.deimos.mongo.StandardMongoTemplate;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

/**
 * 数据访问基类实现类
 * 
 * @author jy.hu
 * 
 * @param <T>
 *            类
 * @param <PK>
 *            主键
 */
@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

	private static final String ID_FIELD = "_id";

	@Autowired
	private StandardMongoTemplate mongoTemplate;

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
	public T findById(PK id) {
		return mongoTemplate.findById(id, entityClass);
	}

	@Override
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public T findOne(Criteria criteria) {
		Query query = Query.query(criteria);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public List<T> findList(Query query) {
		Sort sort = null;
		return findList(query, 0, sort);
	}

	@Override
	public List<T> findList(Query query, Sort... sort) {
		return findList(query, 0, sort);
	}

	@Override
	public List<T> findList(Query query, int limit) {
		Sort sort = null;
		return findList(query, limit, sort);
	}

	@Override
	public List<T> findList(Query query, int limit, Sort... sort) {
		if (query == null)
			query = new Query();
		if (sort != null) {
			for (Sort s : sort) {
				query.with(s);
			}
		}
		if (limit > 0)
			query.limit(limit);
		return mongoTemplate.find(query, entityClass);
	}

	@Override
	public List<T> findListByKeyValue(String key, Object value) {
		return findList(Query.query(Criteria.where(key).is(value)));
	}

	@Override
	public List<T> findListByKeyValues(String key, Collection<Object> values) {
		return findList(Query.query(Criteria.where(key).in(values)));
	}

	@Override
	public List<T> findListByIds(Collection<PK> ids) {
		Query query = Query.query(Criteria.where(ID_FIELD).in(ids));
		return findList(query);
	}

	@Override
	public List<T> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public Page<T> findPage(Query query, Pageable pageable, Sort... sort) {
		if (query == null) {
			query = new Query();
		}
		Long total = getCount(query);
		if (sort != null) {
			for (Sort s : sort) {
				query.with(s);
			}
		}
		query.with(pageable);
		List<T> content = findList(query);
		return new PageImpl<T>(content, pageable, total);
	}

	@Override
	public Long getCount(Query query) {
		return mongoTemplate.count(query, entityClass);
	}

	@Override
	public Long getCount(String key, Object val) {
		Query query = Query.query(Criteria.where(key).is(val));
		return getCount(query);
	}

	@Override
	public T save(T entity) {
		mongoTemplate.save(entity);
		return entity;
	}

	@Override
	public void save(Collection<T> entitys) {
		mongoTemplate.insertAll(entitys);
	}

	@Override
	public void update(T entity) {
		mongoTemplate.update(entity);
	}
	@Override
	public void update(T entity, String... exclude) {
		mongoTemplate.update(entity, exclude);
	}
	@Override
	public int updateById(PK id, Update update) {
		Query query = Query.query(Criteria.where(ID_FIELD).is(id));
		return update(query, update, true);
	}

	@Override
	public int updateByIds(Collection<PK> ids, Update update) {
		Query query = Query.query(Criteria.where(ID_FIELD).in(ids));
		return update(query, update, false);
	}

	@Override
	public int upsert(Query query, Update update) {
		WriteResult wr = mongoTemplate.upsert(query, update, entityClass);
		return wr.getN();
	}

	@Override
	public int update(Query query, Update update, boolean isFirst) {
		WriteResult wr = null;
		if (isFirst) {
			wr = mongoTemplate.updateFirst(query, update, entityClass);
		} else {
			wr = mongoTemplate.updateMulti(query, update, entityClass);
		}
		return wr.getN();
	}

	@Override
	public void remove(Query query) {
		mongoTemplate.remove(query, entityClass);
	}

	@Override
	public void remove(PK id) {
		remove(Query.query(Criteria.where(ID_FIELD).is(id)));
	}

	@Override
	public void remove(T entity) {
		mongoTemplate.remove(entity);
	}

	@Override
	public void remove(Collection<PK> ids) {
		for (PK id : ids) {
			remove(id);
		}
	}

	@Override
	public void dropTable(String collectionName) {
		mongoTemplate.dropCollection(collectionName);
	}

	@Override
	public void dropTable() {
		mongoTemplate.dropCollection(entityClass);
	}

	@Override
	public <O> MapReduceResults<O> mapReduce(Query query,
			String inputCollectionName, String mapFunction,
			String reduceFunction, Class<O> clazz) {
		return mongoTemplate.mapReduce(query, inputCollectionName, mapFunction,
				reduceFunction, clazz);
	}

	@Override
	public <O> GroupByResults<O> group(Criteria criteria,
			String inputCollectionName, GroupBy groupBy, Class<O> clazz) {
		return mongoTemplate.group(criteria, inputCollectionName, groupBy,
				clazz);
	}

	@Override
	public String getCollectionName() {
		return mongoTemplate.getCollectionName(entityClass);
	}

	@Override
	public String getCollectionName(Class<?> clazz) {
		return mongoTemplate.getCollectionName(clazz);
	}

	@Override
	public <O> O findSub(PK id, Class<O> subClazz) {
		return findSub(id, subClazz, null);
	}

	@Override
	public <O> O findSub(PK id, Class<O> subClazz, String subName) {
		String collName = getCollectionName();
		if (StringUtils.isBlank(subName)) {
			subName = getCollectionName(subClazz);
		}
		return mongoTemplate.findSubEntityById(id, collName,
				subClazz, subName);
	}

	@Override
	public List<String> findField(Query query, String fieldName) {
		List<String> fieldList = null;
		String collectionName = getCollectionName();
		if (mongoTemplate.collectionExists(collectionName)) {
			DBObject fld = new BasicDBObject(fieldName, 1);
			DBObject obj = query.getQueryObject();
			DBCursor cursor = mongoTemplate.getCollection(collectionName).find(
					obj, fld);
			if (query.getSortObject() != null) {
				cursor.sort(query.getSortObject());
			}
			if (query.getLimit() > 0) {
				cursor.limit(query.getLimit());
			}
			fieldList = new ArrayList<String>(cursor.size());
			while (cursor.hasNext()) {
				obj = cursor.next();
				if (obj.containsField(fieldName)) {
					fieldList.add(obj.get(fieldName).toString());
				}
			}
			cursor.close();
		}
		return fieldList;
	}

	@Override
	public List<String> findId(Query query) {
		return findField(query, ID_FIELD);
	}

	@Override
	public <O> void saveSub(PK id, O o) {
		saveSub(id, o, null);
	}

	@Override
	public <O> void saveSub(PK id, O o, String subName) {
		String collName = getCollectionName();
		if (StringUtils.isBlank(subName)) {
			subName = getCollectionName(o.getClass());
		}
		mongoTemplate.saveSubEntityById(id, collName, o, subName);
	}

	@Override
	public void findAndRemove(Query query) {
		mongoTemplate.findAndRemove(query, entityClass);

	}

	@Override
	public void findAndUpdate(Query query, Update update) {
		mongoTemplate.findAndModify(query, update, entityClass);
	}

	@Override
	public StandardMongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
}
