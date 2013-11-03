package com.foxinmy.deimos.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.foxinmy.deimos.dao.UserDao;
import com.foxinmy.deimos.field.UserField;
import com.foxinmy.deimos.model.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements
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
		return findOne(Criteria.where(UserField.EMAIL_FIELD).is(email));
	}

	@Override
	public Page<User> pageUser(Pageable pageable) {
		Sort sort = null;
		return findPage(null, pageable, sort);
	}
}
