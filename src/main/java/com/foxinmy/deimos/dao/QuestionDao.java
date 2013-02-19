package com.foxinmy.deimos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.foxinmy.deimos.model.Question;

/**
 * @author liubin
 *
 */
public interface QuestionDao extends MongoRepository<Question, String>, QuestionCustomDao{
	
}
