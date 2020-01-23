package com.jeeasy.engine.jobs;

import javax.ejb.Timer;

public class TimedJobData {
	private ITimedJob timedJob;
	private Timer timer;
	private EnumTimedJobState state;
	
	public TimedJobData(ITimedJob timedJob, EnumTimedJobState state) {
		this.timedJob = timedJob;
		this.state = state;
	}

	public ITimedJob getTimedJob() {
		return timedJob;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public EnumTimedJobState getState() {
		return state;
	}
	
	public void setState(EnumTimedJobState state) {
		this.state = state;
	}
}
