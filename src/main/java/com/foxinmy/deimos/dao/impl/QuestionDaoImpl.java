package com.foxinmy.deimos.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.foxinmy.deimos.dao.QuestionDao;
import com.foxinmy.deimos.model.Question;

@Repository
public class QuestionDaoImpl extends BaseDaoImpl<Question, String> implements
		QuestionDao {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
