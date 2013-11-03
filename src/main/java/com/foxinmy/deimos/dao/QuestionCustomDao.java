package com.foxinmy.deimos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.foxinmy.deimos.model.Question;

public interface QuestionCustomDao extends MongoRepository<Question, String> {

}
