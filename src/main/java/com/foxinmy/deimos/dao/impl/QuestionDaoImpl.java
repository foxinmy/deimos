package com.foxinmy.deimos.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.foxinmy.deimos.dao.QuestionCustomDao;
import com.foxinmy.deimos.model.Question;

public class QuestionDaoImpl implements QuestionCustomDao {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public void insertBatch(List<Question> questions) {
		mongoOperations.insert(questions, Question.class);
	}

}
