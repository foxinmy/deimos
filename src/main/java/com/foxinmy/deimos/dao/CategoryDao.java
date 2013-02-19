package com.foxinmy.deimos.dao;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.foxinmy.deimos.model.Category;
import com.foxinmy.deimos.model.Textbook;

/**
 * @author liubin
 *
 */
public interface CategoryDao extends MongoRepository<Textbook, BigInteger>{
	
	@Query(value = "{ 'name' : ?0 }")
	Category getByName(String name);
	
}
