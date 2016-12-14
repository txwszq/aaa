package com.kedacom.expenses.scheduler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;

import com.kedacom.core.utils.TimeUtil;
import com.kedacom.web.ResultMessage;

/**
 * 定时任务Service
 * @author Zhaozhiqiang
 */
public class SchedulerService {

	@Resource
	Scheduler scheduler;
	private static HashMap<String, String> mapWeek = new HashMap<String, String>();
	private static final String schedGroup = "group1";

	/**
	 * 添加定时任务
	 * @param jobName
	 * @param className
	 * @param parameterJson
	 * @param description
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	public void addJob(String jobName, String className, String parameterJson, String description)
			throws SchedulerException, ClassNotFoundException {
		Class cls = Class.forName(className);
		JobBuilder jb = JobBuilder.newJob(cls);
		jb.withIdentity(jobName, schedGroup);
		if (StringUtils.isNotEmpty(parameterJson)) {
			setJobMap(parameterJson, jb);
		}
		jb.storeDurably();
		jb.withDescription(description);
		JobDetail jobDetail = jb.build();
		this.scheduler.addJob(jobDetail, true);
	}

	public ResultMessage addJob(String jobName, String className, Map<String, Object> parameterMap, String description)
			throws SchedulerException {
		ResultMessage resultMsg = null;
		boolean isJobExist = isJobExists(jobName);
		if (isJobExist) {
			resultMsg = new ResultMessage(ResultMessage.Error, "任务已存在");
			return resultMsg;
		}
		Class cls;
		try {
			cls = Class.forName(className);
		} catch (ClassNotFoundException e) {
			return new ResultMessage(0, "指定的任务类不存在，或者没有实现JOB接口");
		}

		try {
			return addJob(jobName, cls, parameterMap, description);
		} catch (Exception e) {
			resultMsg = new ResultMessage(0, e.getMessage());
		}
		return resultMsg;
	}

	public ResultMessage addJob(String jobName, Class cls, Map<String, Object> parameterMap, String description) {
		ResultMessage resultMsg = null;
		try {
			JobBuilder jb = JobBuilder.newJob(cls);
			jb.withIdentity(jobName, schedGroup);
			if (parameterMap != null) {
				JobDataMap map = new JobDataMap();
				map.putAll(parameterMap);
				jb.usingJobData(map);
			}
			jb.storeDurably();
			jb.withDescription(description);
			JobDetail jobDetail = jb.build();
			this.scheduler.addJob(jobDetail, true);
			return new ResultMessage(1, "添加任务成功!");
		} catch (Exception e) {
			resultMsg = new ResultMessage(0, e.getMessage());
		}
		return resultMsg;
	}

	public boolean isJobExists(String jobName) throws SchedulerException {
		JobKey key = new JobKey(jobName, schedGroup);
		return this.scheduler.checkExists(key);
	}

	public List<JobDetail> getJobList() throws SchedulerException {
		List list = new ArrayList();
		GroupMatcher matcher = GroupMatcher.groupEquals(schedGroup);
		Set<JobKey> set = this.scheduler.getJobKeys(matcher);
		for (JobKey jobKey : set) {
			JobDetail detail = this.scheduler.getJobDetail(jobKey);
			list.add(detail);
		}
		return list;
	}

	public List<Trigger> getTriggersByJob(String jobName) throws SchedulerException {
		JobKey key = new JobKey(jobName, schedGroup);
		return (List<Trigger>) this.scheduler.getTriggersOfJob(key);
	}

	public HashMap<String, Trigger.TriggerState> getTriggerStatus(List<Trigger> list) throws SchedulerException {
		HashMap map = new HashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Trigger trigger = (Trigger) it.next();
			TriggerKey key = trigger.getKey();
			Trigger.TriggerState state = this.scheduler.getTriggerState(key);
			map.put(key.getName(), state);
		}
		return map;
	}

	public boolean isTriggerExists(String trigName) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(trigName, schedGroup);
		return this.scheduler.checkExists(triggerKey);
	}

	/**
	 * 把任务添加到触发器
	 * @param jobName
	 * @param trigName
	 * @param planJson
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void addTrigger(String jobName, String trigName, String planJson) throws SchedulerException, ParseException {
		// 分组的jobKey
		JobKey jobKey = new JobKey(jobName, schedGroup);
		TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger();
		tb.withIdentity(trigName, schedGroup);

		setTrigBuilder(planJson, tb);
		tb.forJob(jobKey);
		Trigger trig = tb.build();
		this.scheduler.scheduleJob(trig);
	}

	public void addTrigger(String jobName, String trigName, int minute) throws SchedulerException {
		JobKey jobKey = new JobKey(jobName, schedGroup);
		TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger();
		tb.withIdentity(trigName, schedGroup);
		ScheduleBuilder sb = CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInMinutes(minute);
		tb.startNow();
		tb.withSchedule(sb);
		tb.withDescription("每:" + minute + "分钟执行!");

		tb.forJob(jobKey);
		Trigger trig = tb.build();
		this.scheduler.scheduleJob(trig);
	}

	public void addTrigger(String jobName, String trigName, Integer second, Boolean flag) throws SchedulerException {
		JobKey jobKey = new JobKey(jobName, schedGroup);
		TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger();
		tb.withIdentity(trigName, schedGroup);
		ScheduleBuilder sb = CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInSeconds(second);
		tb.startNow();
		tb.withSchedule(sb);
		tb.withDescription("每:" + second + "秒执行!");

		tb.forJob(jobKey);
		Trigger trig = tb.build();
		this.scheduler.scheduleJob(trig);
	}

	/**
	 * 设置计划执行的时间
	 * @param planJson
	 * @param tb
	 * @throws ParseException
	 */
	private void setTrigBuilder(String planJson, TriggerBuilder<Trigger> tb) throws ParseException {
		// 前台组装好的字符串数据转换成json对象
		JSONObject jsonObject = JSONObject.fromObject(planJson);
		// 计划执行对象 执行类型 执行间隔
		PlanObject planObject = (PlanObject) JSONObject.toBean(jsonObject, PlanObject.class);
		// 执行方式
		int type = planObject.getType();
		// 执行间隔
		String value = planObject.getTimeInterval();
		switch (type) {
		// 只执行一次
			case 1:
				// 执行时间
				Date date = TimeUtil.convertString(value);
				tb.startAt(date);
				tb.withDescription("执行一次,执行时间:" + date.toLocaleString());
				break;
			// 每隔多少分钟执行一次
			case 2:
				// 间隔时间
				int minute = Integer.parseInt(value);
				ScheduleBuilder sb = CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInMinutes(
						minute);

				tb.startNow();
				tb.withSchedule(sb);
				tb.withDescription("每:" + minute + "分钟执行!");
				break;
			// 每天指定时间执行
			case 3:
				String[] aryTime = value.split(":");
				// 时
				int hour = Integer.parseInt(aryTime[0]);
				// 分
				int m = Integer.parseInt(aryTime[1]);
				ScheduleBuilder sb1 = CronScheduleBuilder.dailyAtHourAndMinute(hour, m);
				tb.startNow();
				tb.withSchedule(sb1);
				tb.withDescription("每天：" + hour + ":" + m + "执行!");
				break;
			// 每周指定时间执行
			case 4:
				String[] aryExpression = value.split("[|]");
				// 星期几
				String week = aryExpression[0];
				String[] aryTime1 = aryExpression[1].split(":");
				// 时
				String h1 = aryTime1[0];
				// 分
				String m1 = aryTime1[1];
				// 执行表达式
				String cronExperssion = "0 " + m1 + " " + h1 + " ? * " + week;
				ScheduleBuilder sb4 = CronScheduleBuilder.cronSchedule(cronExperssion);
				tb.startNow();
				tb.withSchedule(sb4);
				String weekName = getWeek(week);
				tb.withDescription("每周：" + weekName + "," + h1 + ":" + m1 + "执行!");
				break;
			// 每月执行日期执行
			case 5:
				String[] aryExpression5 = value.split("[|]");
				String day = aryExpression5[0];
				String[] aryTime2 = aryExpression5[1].split(":");
				String h2 = aryTime2[0];
				String m2 = aryTime2[1];
				String cronExperssion1 = "0 " + m2 + " " + h2 + " " + day + " * ?";
				ScheduleBuilder sb5 = CronScheduleBuilder.cronSchedule(cronExperssion1);
				tb.startNow();
				tb.withSchedule(sb5);
				String dayName = getDay(day);
				tb.withDescription("每月:" + dayName + "," + h2 + ":" + m2 + "执行!");
				break;
			// 自定义cron表达式
			case 6:
				ScheduleBuilder sb6 = CronScheduleBuilder.cronSchedule(value);
				tb.startNow();
				tb.withSchedule(sb6);
				tb.withDescription("CronTrigger表达式:" + value);
		}
	}

	private String getDay(String day) {
		String[] aryDay = day.split(",");
		int len = aryDay.length;
		String str = "";
		for (int i = 0; i < len; i++) {
			String tmp = aryDay[i];
			tmp = tmp.equals("L") ? "最后一天" : tmp;
			if (i < len - 1) {
				str = str + tmp + ",";
			} else {
				str = str + tmp;
			}
		}
		return str;
	}

	private String getWeek(String week) {
		String[] aryWeek = week.split(",");
		int len = aryWeek.length;
		String str = "";
		for (int i = 0; i < len; i++) {
			if (i < len - 1)
				str = str + (String) mapWeek.get(aryWeek[i]) + ",";
			else
				str = str + (String) mapWeek.get(aryWeek[i]);
		}
		return str;
	}

	private void setJobMap(String json, JobBuilder jb) {
		JSONArray aryJson = JSONArray.fromObject(json);
		ParameterObj[] list = (ParameterObj[]) JSONArray.toArray(aryJson, ParameterObj.class);
		for (int i = 0; i < list.length; i++) {
			ParameterObj obj = list[0];
			String type = obj.getType();
			String name = obj.getName();
			String value = obj.getValue();
			if (type.equals("int")) {
				jb.usingJobData(name, Integer.valueOf(Integer.parseInt(value)));
			} else if (type.equals("long")) {
				jb.usingJobData(name, Long.valueOf(Long.parseLong(value)));
			} else if (type.equals("float")) {
				jb.usingJobData(name, Float.valueOf(Float.parseFloat(value)));
			} else if (type.equals("boolean")) {
				jb.usingJobData(name, Boolean.valueOf(Boolean.parseBoolean(value)));
			} else
				jb.usingJobData(name, value);
		}
	}

	public void delJob(String jobName) throws SchedulerException {
		JobKey key = new JobKey(jobName, schedGroup);
		this.scheduler.deleteJob(key);
	}

	public void delTrigger(String triggerName) throws SchedulerException {
		TriggerKey key = new TriggerKey(triggerName, schedGroup);
		this.scheduler.unscheduleJob(key);
	}

	public void toggleTriggerRun(String triggerName) throws SchedulerException {
		TriggerKey key = new TriggerKey(triggerName, schedGroup);
		Trigger.TriggerState state = this.scheduler.getTriggerState(key);
		if (state == Trigger.TriggerState.PAUSED) {
			this.scheduler.resumeTrigger(key);
		} else if (state == Trigger.TriggerState.NORMAL) this.scheduler.pauseTrigger(key);
	}

	/**
	 * 执行定时任务
	 * @param jobName
	 * @throws SchedulerException
	 */
	public void executeJob(String jobName) throws SchedulerException {
		JobKey key = new JobKey(jobName, schedGroup);
		this.scheduler.triggerJob(key);
	}

	static {
		mapWeek.put("MON", "星期一");
		mapWeek.put("TUE", "星期二");
		mapWeek.put("WED", "星期三");
		mapWeek.put("THU", "星期四");
		mapWeek.put("FRI", "星期五");
		mapWeek.put("SAT", "星期六");
		mapWeek.put("SUN", "星期日");
	}
}
