package com.foxinmy.deimos.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.foxinmy.deimos.dao.BaseDao;
import com.foxinmy.deimos.service.BaseService;

@Service
public class BaseServiceImpl<T, PK extends Serializable> implements
		BaseService<T, PK> {

	private BaseDao<T, PK> baseDao;

	public void loadBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public T findById(PK id) {
		return baseDao.findById(id);
	}

	@Override
	public <O> O findSub(PK id, Class<O> subClazz) {
		return baseDao.findSub(id, subClazz);
	}

	@Override
	public <O> O findSub(PK id, Class<O> subClazz, String subName) {
		return baseDao.findSub(id, subClazz, subName);
	}

	@Override
	public List<T> findListByKeyValue(String key, Object value) {
		return baseDao.findListByKeyValue(key, value);
	}

	@Override
	public List<T> findListByKeyValues(String key, Collection<Object> values) {
		return baseDao.findListByKeyValues(key, values);
	}

	@Override
	public List<T> findListByIds(Collection<PK> ids) {
		return baseDao.findListByIds(ids);
	}

	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}

	@Override
	public Long getCount() {
		return baseDao.getCount(null);
	}

	@Override
	public Long getCount(String key, Object val) {
		return baseDao.getCount(key, val);
	}

	@Override
	public T save(T entity) {
		return baseDao.save(entity);
	}

	@Override
	public <O> void saveSub(PK id, O o) {
		baseDao.saveSub(id, o);
	}

	@Override
	public <O> void saveSub(PK id, O o, String subName) {
		baseDao.saveSub(id, o, subName);
	}

	@Override
	public void save(Collection<T> entitys) {
		baseDao.save(entitys);
	}

	@Override
	public void update(T entity) {
		baseDao.update(entity);
	}

	@Override
	public void remove(T entity) {
		baseDao.update(entity);
	}

	@Override
	public void remove(PK id) {
		baseDao.remove(id);
	}

	@Override
	public void remove(Collection<PK> ids) {
		baseDao.remove(ids);
	}

	@Override
	public String getCollectionName() {
		return baseDao.getCollectionName();
	}

	@Override
	public String getCollectionName(Class<?> clazz) {
		return baseDao.getCollectionName(clazz);
	}
}
