package com.foxinmy.deimos.dao;

import java.util.List;

import com.foxinmy.deimos.model.Question;

public interface QuestionCustomDao {

	/**
	 * 批量插入问题
	 * @param words
	 */
	void insertBatch(List<Question> questions);
	
	
}
