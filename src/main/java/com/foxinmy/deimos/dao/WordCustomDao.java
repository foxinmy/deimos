package com.foxinmy.deimos.dao;

import java.util.List;

import com.foxinmy.deimos.model.Word;

public interface WordCustomDao {

	/**
	 * 批量插入单词
	 * @param words
	 */
	void insertBatch(List<Word> words);
	
	/**
	 * 随机得到一个单词
	 * @return
	 */
	Word findRandomOne();
	
}
