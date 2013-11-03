package com.foxinmy.deimos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.foxinmy.deimos.model.Category;
import com.foxinmy.deimos.model.Question;

public interface CategoryCustomDao extends MongoRepository<Question, String>{

	@Query(value = "{ 'name' : ?0 }")
	Category getByName(String name);
}
