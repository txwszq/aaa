package com.kedacom.expenses.scheduler;

/**
 * 计划对象
 * 存放计划类型,与执行时间的
 * @author Zhaozhiqiang
 *
 */
public class PlanObject {
	private int type = 0;

	private String timeInterval = "";

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTimeInterval() {
		return this.timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
}
