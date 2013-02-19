package com.foxinmy.deimos.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxinmy.deimos.dao.UserDao;
import com.foxinmy.deimos.model.User;

@Service
public class UserService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final int LOGIN_SUCCESS = 0;
	public static final int USER_NOT_FOUND = -1;
	public static final int PASSWORD_NOT_MATCH = -2;

	@Autowired
	private UserDao userDao;

	public int userLogin(User user) {
		User loginUser = userDao.login(user.getEmail());
		if (loginUser != null) {
			if (DigestUtils.md5Hex(user.getPassword()).equals(
					loginUser.getPassword())) {
				user = loginUser;
				return LOGIN_SUCCESS;
			} else {
				return PASSWORD_NOT_MATCH;
			}
		} else {
			return USER_NOT_FOUND;
		}
	}
}
