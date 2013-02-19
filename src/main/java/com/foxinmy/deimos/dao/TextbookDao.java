package com.foxinmy.deimos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.foxinmy.deimos.model.Textbook;

/**
 * @author liubin
 *
 */
public interface TextbookDao extends MongoRepository<Textbook, String>, TextbookCustomDao {
	
	@Query(value = "{ 'name' : ?0 }")
	Textbook getByName(String name);
	
}
