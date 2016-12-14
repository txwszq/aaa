package com.kedacom.expenses.scheduler;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kedacom.core.utils.BeanUtils;
import com.kedacom.expenses.dao.SysJobLogDao;
import com.kedacom.expenses.model.SysJobLog;
import com.kedacom.security.util.UniqueIdUtil;

/**
 * 定时任务的基类,自定义定时任务,继承该类就可以了
 * @author Zhaozhiqiang
 */
public abstract class BaseJob implements Job {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public abstract void executeJob(JobExecutionContext paramJobExecutionContext) throws Exception;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String jobName = context.getJobDetail().getKey().getName();

		String trigName = "directExec";
		Trigger trig = context.getTrigger();
		if (trig != null) trigName = trig.getKey().getName();
		Date strStartTime = new Date();
		long startTime = System.currentTimeMillis();
		try {
			executeJob(context);
			long endTime = System.currentTimeMillis();
			Date strEndTime = new Date();

			long runTime = (endTime - startTime) / 1000L;
			addLog(jobName, trigName, strStartTime, strEndTime, runTime, "任务执行成功!", 1);
		} catch (Exception ex) {
			ex.printStackTrace();
			long endTime = System.currentTimeMillis();
			Date strEndTime = new Date();
			long runTime = (endTime - startTime) / 1000L;
			try {
				addLog(jobName, trigName, strStartTime, strEndTime, runTime, ex.toString(), 0);
			} catch (Exception e) {
				e.printStackTrace();
				this.log.error("执行任务出错:" + e.getMessage());
			}
		}
	}

	private void addLog(String jobName, String trigName, Date strStartTime, Date strEndTime, long runTime,
			String content, int state) throws Exception {
		SysJobLogDao dao = (SysJobLogDao) BeanUtils.getBean(SysJobLogDao.class);

		SysJobLog model = new SysJobLog();

		Long id = Long.valueOf(UniqueIdUtil.genId());

		model.setLogId(id);
		model.setJobName(jobName);
		model.setTrigName(trigName);
		model.setStartTime(strStartTime);
		model.setEndTime(strEndTime);
		model.setContent(content);
		model.setState(state);
		model.setRunTime(Long.valueOf(runTime));
		dao.add(model);
	}
}
