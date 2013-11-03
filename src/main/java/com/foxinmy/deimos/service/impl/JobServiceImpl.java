package com.foxinmy.deimos.service.impl;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxinmy.deimos.job.BuildUserContentHtmlJob;
import com.foxinmy.deimos.service.JobService;
import com.foxinmy.deimos.util.SettingUtil;

@Service
public class JobServiceImpl implements JobService {

	private static final Logger LOG = LoggerFactory
			.getLogger(JobServiceImpl.class);

	@Autowired
	private Scheduler scheduler;

	@Override
	public void buildUserContentHtml(String id) {
		try {
			String jobName = BuildUserContentHtmlJob.JOB_NAME;
			String triggerName = BuildUserContentHtmlJob.TRIGGER_NAME;
			String groupName = BuildUserContentHtmlJob.GROUP_NAME;

			Date startTime = DateBuilder.nextGivenSecondDate(null, SettingUtil
					.getSetting().getBuildHtmlDelayTime());
			JobKey jobKey = jobKey(jobName, groupName);
			TriggerKey triggerKey = triggerKey(triggerName, groupName);

			Trigger trigger = newTrigger()
					.startAt(startTime)
					.withIdentity(triggerKey)
					.withSchedule(
							simpleSchedule().withRepeatCount(1)
									.withIntervalInSeconds(10)).build();

			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			if (jobDetail != null) {
				scheduler.resumeJob(jobKey);
				scheduler.resumeTrigger(triggerKey);
				if (scheduler.isShutdown()) {
					scheduler.start();
				}
			} else {
				jobDetail = newJob(BuildUserContentHtmlJob.class).withIdentity(
						jobKey).build();
				jobDetail.getJobDataMap().put("id", id);
				scheduler.scheduleJob(jobDetail, trigger);
				scheduler.start();
			}
		} catch (SchedulerException e) {
			LOG.error("build user content html error.....{}", e.getMessage());
			e.printStackTrace();
		}
	}
}
