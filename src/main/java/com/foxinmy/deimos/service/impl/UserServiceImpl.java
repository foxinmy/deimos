package com.foxinmy.deimos.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.foxinmy.deimos.bean.UserConstantBean;
import com.foxinmy.deimos.dao.UserDao;
import com.foxinmy.deimos.model.User;
import com.foxinmy.deimos.service.UserService;
import com.foxinmy.jycore.constant.ConstantBean;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements
		UserService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		super.loadBaseDao(userDao);
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	public ConstantBean<Integer> userLogin(User user) {
		User loginUser = userDao.login(user.getEmail());
		if (loginUser != null) {
			if (DigestUtils.md5Hex(user.getPassword()).equals(
					loginUser.getPassword())) {
				BeanUtils.copyProperties(loginUser, user);
				return UserConstantBean.UserLogin.LOGIN_SUCCESS;
			} else {
				return UserConstantBean.UserLogin.PASSWORD_NOT_MATCH;
			}
		} else {
			return UserConstantBean.UserLogin.USER_NOT_FOUND;
		}
	}

	/**
	 * 用户是否注册
	 * 
	 * @param email
	 * @return
	 */
	public boolean userIsExists(String email) {
		return userDao.login(email) != null;
	}
}
