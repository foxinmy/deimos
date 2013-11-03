package com.foxinmy.deimos.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.foxinmy.deimos.bean.MailTemplateConfig;
import com.foxinmy.deimos.model.User;
import com.foxinmy.deimos.service.MailService;
import com.foxinmy.deimos.util.SettingUtil;
import com.foxinmy.deimos.util.TemplateConfigUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class MailServiceImpl implements MailService,ServletContextAware {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Autowired
	private TaskExecutor taskExecutor;

	private ServletContext servletContext;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	// 邮件发送任务
	public void addSendMailTask(final MimeMessage mimeMessage) {
		try {
			taskExecutor.execute(new Runnable() {
				public void run() {
					javaMailSender.send(mimeMessage);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 发送邮件
	public void sendMail(String subject, String templatePath,
			Map<String, Object> data, String toMail) {
		try {
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) javaMailSender;
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			Configuration configuration = freeMarkerConfigurer
					.getConfiguration();
			freeMarkerConfigurer.setServletContext(servletContext);
			Template template = configuration.getTemplate(templatePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(
					template, data);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
					mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord("deimos <"
					+ javaMailSenderImpl.getUsername() + ">"));
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			// javaMailSender.send(mimeMessage);
			addSendMailTask(mimeMessage);
		} catch (Exception e) {
			logger.error("MailServiceImpl:发送邮件异常------{}",e.getMessage());
			e.printStackTrace();
		}
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
	public void sendUserVerifyEmail(User user) {
        Map<String, Object> data = getCommonData();
        data.put("user", user);
        MailTemplateConfig mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(MailTemplateConfig.USER_VERIFY);
        String subject = mailTemplateConfig.getSubject();
        String templatePath = mailTemplateConfig.getTemplatePath();
        sendMail(subject, templatePath, data, user.getEmail());
	}
}
