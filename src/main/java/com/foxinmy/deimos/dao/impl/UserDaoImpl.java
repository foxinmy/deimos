package com.foxinmy.deimos.dao.impl;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.foxinmy.deimos.dao.UserDao;
import com.foxinmy.deimos.model.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, BigInteger> implements
		UserDao {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*@Autowired
	private MongoOperations mongoOperations;*/

	/**
	 * 用户登录
	 * 
	 * @param email
	 * @return
	 */
	public User login(String email) {
		return findOne(Criteria.where("email").is(email));
	}
}
