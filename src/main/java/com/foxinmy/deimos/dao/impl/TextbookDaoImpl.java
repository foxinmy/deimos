package com.foxinmy.deimos.dao.impl;

import com.foxinmy.deimos.dao.TextbookCustomDao;

public class TextbookDaoImpl implements TextbookCustomDao {

	@Override
	public void doSomething(String name) {
		System.out.println("hello ," + name);
	}

}
