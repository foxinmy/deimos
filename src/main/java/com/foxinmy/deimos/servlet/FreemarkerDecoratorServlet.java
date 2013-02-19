package com.foxinmy.deimos.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxinmy.jycore.util.StringUtil;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

/**
 * 
 * @title FreemarkerDecoratorServlet.java
 * @description 当title为空时显示默认
 * @author jy.hu , 2013-1-30
 */
public class FreemarkerDecoratorServlet extends
		com.opensymphony.module.sitemesh.freemarker.FreemarkerDecoratorServlet {

	private static final long serialVersionUID = 2448165607350766489L;

	private static final String DECORATOR_TITLE_MAP_NAME = "title";

	@Override
	protected boolean preTemplateProcess(HttpServletRequest request,
			HttpServletResponse response, Template template,
			TemplateModel templateModel) throws ServletException, IOException {
		boolean result = super.preTemplateProcess(request, response, template,
				templateModel);
		SimpleHash hash = (SimpleHash) templateModel;
		HTMLPage htmlPage = (HTMLPage) request
				.getAttribute(RequestConstants.PAGE);
		if (StringUtil.isBlank(htmlPage.getTitle()))
			hash.remove(DECORATOR_TITLE_MAP_NAME);
		return result;
	}

}
