package com.foxinmy.deimos.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.foxinmy.deimos.model.Word;

/**
 * @author liubin
 *
 */
public interface WordDao extends MongoRepository<Word, String>, WordCustomDao{
	
	@Query(value = "{'textbook': {'$ref': 'textbook', '$id': {'$oid': ?0 } } }")
	List<Word> findByTextbookId(String textbookId);
	
	@Query(value = "{ 'spell' : ?0 }")
	Word getBySpell(String word);
	
}
