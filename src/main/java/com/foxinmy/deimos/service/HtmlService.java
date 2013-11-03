package com.foxinmy.deimos.service;

import java.util.Map;

import com.foxinmy.deimos.model.User;

public interface HtmlService {
	/**
	 * 根据Freemarker模板文件路径、Map数据生成HTML
	 * 
	 * @param templatePath
	 *            Freemarker模板文件路径
	 *            
	 * @param htmlPath
	 *            生成HTML路径
	 * 
	 * @param data
	 *            Map数据
	 * 
	 */
	public void buildHtml(String templatePath, String htmlPath, Map<String, Object> data);
	

	/**
	 * 根据用户ID生成HTML
	 * @param id
	 */
	public void buildUserContentHtml(String id);
	/**
	 * 根据用户实体生成HTML
	 * @param user
	 */
	public void buildUserContentHtml(User user);
}
