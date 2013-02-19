package com.foxinmy.deimos.dao;

import java.math.BigInteger;

import com.foxinmy.deimos.model.User;

/**
 * 用户数据访问
 * @author jy.hu
 * 2012-08-08 18:08:41
 */
public interface UserDao extends BaseDao<User, BigInteger> {
	/**
	 * 用户登录
	 * @param email
	 * @return
	 */
	public User login(String email);
}
