package com.foxinmy.deimos.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.foxinmy.deimos.mongo.StandardMongoTemplate;

/**
 * 数据访问基类接口
 * 
 * @author jy.hu
 * 
 * @param <T>
 *            类
 * @param <PK>
 *            主键
 */
public interface BaseDao<T, PK extends Serializable> {
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
	 * 根据条件获取实体对象
	 * 
	 * @param query
	 *            条件
	 * @return 实体对象
	 */
	public T findOne(Query query);
	
	/**
	 * 
	 * @param criteria
	 * @return
	 */
	public T findOne(Criteria criteria);

	/**
	 * 根据条件获取实体集合
	 * 
	 * @param query
	 * @return
	 */
	public List<T> findList(Query query);

	/**
	 * 获取集合
	 * 
	 * @param query
	 *            查询条件
	 * @param sort
	 *            排序字典
	 * @return
	 */
	public List<T> findList(Query query, Sort... sort);

	/**
	 * 查询集合
	 * 
	 * @param query
	 *            查询条件
	 * @param limit
	 *            限制结果数
	 * @return
	 */
	public List<T> findList(Query query, int limit);

	/**
	 * 查询集合
	 * 
	 * @param query
	 *            查询条件
	 * @param sort
	 *            排序字典
	 * @param limit
	 *            限制结果数
	 * @return
	 */
	public List<T> findList(Query query, int limit, Sort... sort);

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
	 * 查询分页(注意:该函数只能适用于小数据集合)
	 * 
	 * @param query
	 * @param sort
	 * @param pageable
	 * @return
	 */
	public Page<T> findPage(Query query, Pageable pageable, Sort... sort);

	/**
	 * 查询单个字段的值
	 * 
	 * @param query
	 * @param fieldName
	 * @return
	 */
	public List<String> findField(Query query, String fieldName);

	/**
	 * 查询ID字段
	 * 
	 * @param query
	 * @return
	 */
	public List<String> findId(Query query);

	/**
	 * 获取所有实体对象总数
	 * 
	 * @param 查询对象
	 * @return 实体对象总数
	 */
	public Long getCount(Query query);

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
	 * @param lName
	 *            )
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
	 * 更新实体对象
	 * @param entity
	 * @param exclude
	 */
	public void update(T entity, String... exclude);

	/**
	 * 根据ID更新实体
	 * 
	 * @param id
	 * @param update
	 */
	public int updateById(PK id, Update update);

	/**
	 * 根据ID集合更新实体
	 * 
	 * @param ids
	 * @param update
	 * @return
	 */
	public int updateByIds(Collection<PK> ids, Update update);

	/**
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public int upsert(Query query, Update update);

	/**
	 * 
	 * @param query
	 *            查询条件
	 * @param update
	 *            更新参数
	 * @param isFirst
	 *            单行更新标识
	 * @return
	 */
	public int update(Query query, Update update, boolean isFirst);

	/**
	 * 移除某记录
	 * 
	 * @param query
	 */
	public void remove(Query query);

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            对象
	 * @return
	 */
	public void remove(T entity);

	/**
	 * 
	 * @param query
	 */
	public void findAndRemove(Query query);

	/**
	 * 
	 * @param query
	 * @param update
	 */
	public void findAndUpdate(Query query, Update update);

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
	 * 删除集合
	 */

	public void dropTable();

	/**
	 * 删除集合
	 * 
	 * @param collectionName
	 *            集合名
	 */
	public void dropTable(String collectionName);

	public <O> MapReduceResults<O> mapReduce(Query query,
			String inputCollectionName, String mapFunction,
			String reduceFunction, Class<O> clazz);

	public <O> GroupByResults<O> group(Criteria criteria,
			String inputCollectionName, GroupBy groupBy, Class<O> clazz);

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

	/**
	 * 获取mongotemplate
	 * 
	 * @return
	 */
	public StandardMongoTemplate getMongoTemplate();
}
