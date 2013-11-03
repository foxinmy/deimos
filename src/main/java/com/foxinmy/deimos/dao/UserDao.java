package com.foxinmy.deimos.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxinmy.deimos.model.User;

/**
 * 用户数据访问
 * @author jy.hu
 * 2012-08-08 18:08:41
 */
public interface UserDao extends BaseDao<User, String> {
	/**
	 * 用户登录
	 * @param email
	 * @return
	 */
	public User login(String email);
	
	public Page<User> pageUser(Pageable pageable);
}
