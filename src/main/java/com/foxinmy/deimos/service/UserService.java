package com.foxinmy.deimos.service;

import com.foxinmy.deimos.model.User;
import com.foxinmy.jycore.constant.ConstantBean;

public interface UserService extends BaseService<User, String> {
	
	public ConstantBean<Integer> userLogin(User user);

	public boolean userIsExists(String email);
}
