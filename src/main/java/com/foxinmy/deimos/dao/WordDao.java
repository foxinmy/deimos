package com.foxinmy.deimos.dao;

import com.foxinmy.deimos.model.Word;

/**
 * @author liubin
 *
 */
public interface WordDao extends BaseDao<Word, String>{
	
	/**
	 * 随机得到一个单词
	 * @return
	 */
	Word findRandomOne();
}
