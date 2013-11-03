package com.foxinmy.deimos.job;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.foxinmy.deimos.service.HtmlService;
import com.foxinmy.deimos.util.SpringUtil;

/**
 * 生成用户内容html作业
 * 
 * @title BuildUserContentHtmlJob.java
 * @description
 * @author jy.hu , 2013-6-12
 */
public class BuildUserContentHtmlJob implements Job {

	public static final String JOB_NAME = "buildUserContentHtmlJob";// 任务名称
	public static final String TRIGGER_NAME = "buildUserContentHtmlTrigger";// Trigger名称
	public static final String GROUP_NAME = "buildUserContentHtmlGroup";// Group名称

	private HtmlService htmlService;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		htmlService = (HtmlService) SpringUtil.getBean("htmlServiceImpl");
		Map<?, ?> jobDataMap = context.getJobDetail().getJobDataMap();
		String id = (String) jobDataMap.get("id");
		htmlService.buildUserContentHtml(id);
	}
}
