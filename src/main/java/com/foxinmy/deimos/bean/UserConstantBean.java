package com.foxinmy.deimos.bean;

import com.foxinmy.jycore.constant.*;

public class UserConstantBean {
	
	/**
	 * 用户表中的性别常量
	 */
	@ConstantModule("user")
	public static class UserGender {
		public final static ConstantBean<Character> GENDER_MALE = new ConstantBean<Character>(
				"男", 'm');
		public final static ConstantBean<Character> GENDER_FEMALE = new ConstantBean<Character>(
				"女", 'f');
	}
	/**
	 * 用户表中的状态常量
	 */
	@ConstantModule("user")
	public static class UserStatus {
        public final static ConstantBean<Integer> STATUS_DISABLED = new ConstantBean<Integer>("未激活",0);
        public final static ConstantBean<Integer> STATUS_USABLED = new ConstantBean<Integer>("已激活",1);
	}
	
	/**
	 * 用户表中的状态常量
	 */
	@ConstantModule("user")
	public static class UserLogin {
        public final static ConstantBean<Integer> LOGIN_SUCCESS = new ConstantBean<Integer>("登录成功！",0);
        public final static ConstantBean<Integer> USER_NOT_FOUND = new ConstantBean<Integer>("用户不存在,请注册！",-1);
        public final static ConstantBean<Integer> PASSWORD_NOT_MATCH = new ConstantBean<Integer>("用户名或密码输入错误!",-2);
	}
}
