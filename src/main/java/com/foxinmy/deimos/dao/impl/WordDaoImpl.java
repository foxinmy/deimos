package com.foxinmy.deimos.dao.impl;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.foxinmy.deimos.dao.WordDao;
import com.foxinmy.deimos.model.Word;

@Repository
public class WordDaoImpl extends BaseDaoImpl<Word, String> implements
		WordDao {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Word findRandomOne() {
		Query query = new Query();
		query.addCriteria(Criteria.where("valid").is(Boolean.TRUE)
				.and("phrase").is(Boolean.FALSE));
		long size = this.getCount(query);
		logger.debug(" words size in findRandomOne method of WordDaoImpl:{} ",
				size);
		if (size == 0L)
			return null;
		query.skip(RandomUtils.nextInt((int) size)).limit(1);
		List<Word> words = this.findList(query);
		return words.isEmpty() ? null : words.get(0);
	}
}
