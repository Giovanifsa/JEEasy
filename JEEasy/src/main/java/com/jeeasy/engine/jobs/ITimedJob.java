package com.jeeasy.engine.jobs;

import javax.ejb.ScheduleExpression;

public interface ITimedJob {
	public ScheduleExpression getSchedule();
	public void runJob();
}
