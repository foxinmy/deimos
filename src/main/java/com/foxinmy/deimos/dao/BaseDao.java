package com.foxinmy.deimos.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
/**
 * 数据访问基类接口
 * @author jy.hu
 *
 * @param <T> 类
 * @param <PK> 主键
 */
public interface BaseDao<T, PK extends Serializable> {
	  /**
     * 根据ID获取实体对象
     * @param id
     *            记录ID
     * @return 实体对象
     */
    public T findOne(PK id);
    /**
     * 根据条件获取实体对象
     * @param criteria 条件
     * @return 实体对象
     */
    public T findOne(Criteria criteria);
    /**
     * 获取所有实体对象集合
     * @return 实体对象集合
     */
    public List<T> findAll();

    /**
     * 获取所有实体对象总数
     * @return 实体对象总数
     */
    public Long getTotalCount();

    /**
     * 保存实体对象
     * @param entity
     *            对象
     * @return ID
     */
    public void save(T entity);
    /**
     * 保存多个实体对象
     * @param entitys
     */
    public void save(List<T> entitys);

    /**
     * 更新实体对象
     * @param entity
     *            对象
     */
    public void update(T entity);

    /**
     * 删除实体对象
     * @param entity
     *            对象
     * @return
     */
    public void delete(T entity);

    /**
     * 根据ID删除实体对象
     * @param id
     *            记录ID
     */
    public void delete(PK id);

    /**
     * 根据ID数组删除实体对象
     * @param ids
     *            ID数组
     */
    public void delete(PK[] ids);
}
