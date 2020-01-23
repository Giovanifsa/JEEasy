package com.jeeasy.engine.jobs;

import javax.ejb.ScheduleExpression;
import javax.inject.Inject;

import com.jeeasy.engine.settings.SettingsManager;

public class CheckConfigChangesJob implements ITimedJob {
	@Inject
	private SettingsManager settingsManager;
	
	@Override
	public ScheduleExpression getSchedule() {
		ScheduleExpression schedule = new ScheduleExpression();
		schedule.second(0);
		
		return schedule;
	}

	@Override
	public void runJob() {
		settingsManager.loadSettingsIfConfigFileChanged();
	}

}
