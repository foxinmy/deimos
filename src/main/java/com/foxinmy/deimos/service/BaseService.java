package com.foxinmy.deimos.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * service访问基类
 * 
 * @title BaseService.java
 * @author jy.hu , 2013-10-31
 */
public interface BaseService<T, PK extends Serializable> {
	/**
	 * 根据ID查询实体对象
	 * 
	 * @param id
	 * @return
	 */
	public T findById(PK id);

	/**
	 * 根据ID查询子实体对象
	 * 
	 * @param id
	 * @param subClazz
	 * @return
	 */
	public <O> O findSub(PK id, Class<O> subClazz);

	/**
	 * 根据ID查询子实体对象
	 * 
	 * @param id
	 * @param subClazz
	 * @param subName
	 * @return
	 */
	public <O> O findSub(PK id, Class<O> subClazz, String subName);

	/**
	 * 键值对查询集合
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public List<T> findListByKeyValue(String key, Object value);

	/**
	 * 键值对查询集合
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public List<T> findListByKeyValues(String key, Collection<Object> values);

	/**
	 * id数组查询集合
	 * 
	 * @param ids
	 *            ID集合
	 * @return
	 */
	public List<T> findListByIds(Collection<PK> ids);

	/**
	 * 获取所有实体对象集合
	 * 
	 * @return 实体对象集合
	 */
	public List<T> findAll();

	/**
	 * 获取所有实体对象总数
	 * 
	 * @return 实体对象总数
	 */
	public Long getCount();

	/**
	 * 获取父数量
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public Long getCount(String key, Object val);

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            对象
	 * @return ID
	 */
	public T save(T entity);

	/**
	 * 保存子文档对象
	 * 
	 * @param id
	 * @param o
	 */
	public <O> void saveSub(PK id, O o);

	/**
	 * 保存子文档对象
	 * 
	 * @param id
	 * @param o
	 * @param subName
	 */
	public <O> void saveSub(PK id, O o, String subName);

	/**
	 * 保存多个实体对象
	 * 
	 * @param entitys
	 */
	public void save(Collection<T> entitys);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            对象
	 */
	public void update(T entity);

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            对象
	 * @return
	 */
	public void remove(T entity);

	/**
	 * 根据ID删除实体对象
	 * 
	 * @param id
	 *            记录ID
	 */
	public void remove(PK id);

	/**
	 * 根据ID数组删除实体对象
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void remove(Collection<PK> ids);

	/**
	 * 获取集合的名称
	 * 
	 * @return
	 */
	public String getCollectionName();

	/**
	 * 获取集合的名称
	 * 
	 * @param clazz
	 *            全限名
	 * @return
	 */
	public String getCollectionName(Class<?> clazz);
}
