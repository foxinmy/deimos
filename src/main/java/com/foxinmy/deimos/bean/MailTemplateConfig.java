package com.foxinmy.deimos.bean;

public class MailTemplateConfig extends PageTemplateConfig {
	
	public static final String USER_VERIFY = "userVerify";// 密码找回
	
	private String subject;// 主题

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
