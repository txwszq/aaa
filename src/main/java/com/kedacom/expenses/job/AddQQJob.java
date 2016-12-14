package com.kedacom.expenses.job;

import org.quartz.JobExecutionContext;

import com.kedacom.core.utils.ApplicationUtil;
import com.kedacom.expenses.scheduler.BaseJob;
import com.kedacom.expenses.service.qq.QQNeedAddService;

public class AddQQJob extends BaseJob {

	private QQNeedAddService needAdd = (QQNeedAddService) ApplicationUtil.getBean(QQNeedAddService.class);

	@Override
	public void executeJob(JobExecutionContext paramJobExecutionContext) throws Exception {
		needAdd.addQQ();
	}

}
