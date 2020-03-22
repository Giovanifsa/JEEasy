package com.jeeasy.engine.jobs;

import javax.ejb.ScheduleExpression;
import javax.inject.Inject;

import com.jeeasy.engine.services.UserAuthorizationService;

public class AuthorizationCleanupJob implements ITimedJob {
	@Inject
	private UserAuthorizationService authService;
	
	@Override
	public ScheduleExpression getSchedule() {
		ScheduleExpression scheduleExpression = new ScheduleExpression();
		scheduleExpression.hour("*");
		scheduleExpression.minute("*");
		
		return scheduleExpression;
	}

	@Override
	public void runJob() {
		authService.cleanupExpiredAuthorizations();
	}
}
