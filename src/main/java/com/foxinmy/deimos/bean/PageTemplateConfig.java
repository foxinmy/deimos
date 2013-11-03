package com.foxinmy.deimos.bean;


public class PageTemplateConfig {
	
	public static final String USER_CONTENT = "userContent";// 用户内容
	
	private String name;// 配置名称
	private String description;// 描述
	private String templatePath;// Freemarker模板文件路径
	private String htmlPath;// 生成HTML静态文件路径

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}
	
}
