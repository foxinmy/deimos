package com.foxinmy.deimos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.foxinmy.deimos.model.Textbook;

public interface TextbookCustomDao extends MongoRepository<Textbook, String>{
	
	@Query(value = "{ 'name' : ?0 }")
	Textbook getByName(String name);
}
