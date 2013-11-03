package com.foxinmy.deimos.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.foxinmy.deimos.bean.PageTemplateConfig;
import com.foxinmy.deimos.model.User;
import com.foxinmy.deimos.service.HtmlService;
import com.foxinmy.deimos.service.UserService;
import com.foxinmy.deimos.util.SettingUtil;
import com.foxinmy.deimos.util.TemplateConfigUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class HtmlServiceImpl implements HtmlService,ServletContextAware{
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ServletContext servletContext;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private UserService userService;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
    // 获取公共数据
    private Map<String, Object> getCommonData() {
        Map<String, Object> commonData = new HashMap<String, Object>();
        //ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
        //ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
        //commonData.put("bundle", resourceBundleModel);
        commonData.put("base", servletContext.getContextPath());
        commonData.put("setting", SettingUtil.getSetting());
        return commonData;
    }
	@Override
	public void buildHtml(String templatePath, String htmlPath, Map<String, Object> data) {
		try {
			freeMarkerConfigurer.setServletContext(servletContext);
			Configuration configuration = freeMarkerConfigurer
					.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			File htmlFile = new File(servletContext.getRealPath(htmlPath));
			File htmlDirectory = htmlFile.getParentFile();
			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("build html error.....{}",e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void buildUserContentHtml(String id) {
		User user = userService.findById(id);
		buildUserContentHtml(user);
	}

	@Override
	public void buildUserContentHtml(User user) {
		if(user == null){
			logger.debug("build html error.....the user is null!");
			return;
		}
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.USER_CONTENT);
		Map<String, Object> data = getCommonData();
		data.put("user", user);
		String htmlPath = pageTemplateConfig.getHtmlPath().replace("{date}", new SimpleDateFormat("yyyyMM").format(new Date())).replace("{uuid}", user.getId());
		buildHtml(pageTemplateConfig.getTemplatePath(), htmlPath , data);
	}
}
