package com.foxinmy.deimos.service;

import java.util.Map;

import com.foxinmy.deimos.model.User;

public interface MailService {
	/**
	 * 根据主题、Freemarker模板文件路径、接收邮箱地址、Map数据发送邮件(异步发送)
	 * 
	 * @param subject
	 *            主题
	 * 
	 * @param templatePath
	 *            Freemarker模板文件路径
	 * 
	 * @param data
	 *            Map数据
	 * 
	 * @param toMail
	 *            收件人邮箱
	 * 
	 */
	public void sendMail(String subject, String templatePath,
			Map<String, Object> data, String toMail);

	/**
	 * 发送用户激活邮件
	 * 
	 * @param user
	 */
	public void sendUserVerifyEmail(User user);
}
