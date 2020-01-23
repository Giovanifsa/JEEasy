package com.jeeasy.engine.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeeasy.engine.configuration.ArchitectureConfigurator;
import com.jeeasy.engine.utils.cdi.CDIUtils;

@Singleton
@Startup
@DependsOn(ArchitectureConfigurator.ArchitectureConfiguratorName)
public class TimedJobManager {
	@Resource
	private TimerService timerService;
	
	@Inject
	private Instance<ITimedJob> timedJobBeans;
	
	private Logger logger = LogManager.getLogger(TimedJobManager.class);
	
	private HashMap<Class<? extends ITimedJob>, TimedJobData> timedJobs = new HashMap<>();
	
	@PostConstruct
	public void postConstruct() {
		logger.info("Initializing Timed Job Manager");
		logger.info("Now registering jobs");
		
		//Default job timers
		for (ITimedJob job : timedJobBeans) {
			addTimer(job);
		}
	}
	
	public void addTimer(ITimedJob timedJob) {
		if (!timedJobs.containsKey(timedJob.getClass())) {
			TimedJobData jobData = new TimedJobData(timedJob, EnumTimedJobState.DISABLED);
			timedJobs.put(timedJob.getClass(), jobData);
			
			logger.info("Registered job \"{}\".", timedJob.getClass().getName());
			
			scheduleTimer(timedJob.getClass());
		}
	}
	
	public void addTimer(Class<? extends ITimedJob> timedJobClass) {
		ITimedJob timedJob = CDIUtils.inject(timedJobClass);
		addTimer(timedJob);
	}
	
	public void disableTimer(Class<? extends ITimedJob> timedJobClass) {
		if (timedJobs.containsKey(timedJobClass)) {
			TimedJobData jobData = timedJobs.get(timedJobClass);
			
			if (jobData.getState() != EnumTimedJobState.DISABLED) {
				jobData.getTimer().cancel();
				jobData.setTimer(null);
				
				jobData.setState(EnumTimedJobState.DISABLED);
				
				logger.info("Disabling job \"{}\".", timedJobClass.getName());
			}
		}
	}
	
	public void scheduleTimer(Class<? extends ITimedJob> timedJobClass) {
		if (timedJobs.containsKey(timedJobClass)) {
			TimedJobData jobData = timedJobs.get(timedJobClass);
			Timer timer = timerService.createCalendarTimer(jobData.getTimedJob().getSchedule());
			
			jobData.setTimer(timer);
			jobData.setState(EnumTimedJobState.SCHEDULED);
			
			logger.info("Scheduled Job \"{}\"", timedJobClass.getName());
		}
	}
	
	public List<TimedJobData> getTimedJobs() {
		return new ArrayList<>(timedJobs.values());
	}
	
	@Timeout
	public void executeTimer(Timer timer) {
		for (Entry<Class<? extends ITimedJob>, TimedJobData> entry : timedJobs.entrySet()) {
			if (entry.getValue().getTimer().equals(timer)) {
				logger.info("Running scheduled job \"" + entry.getValue().getTimedJob().getClass().getName() + "\".");
				Long startMoment = System.currentTimeMillis();
				
				entry.getValue().getTimedJob().runJob();
				
				logger.info("Scheduled job \"{}\" completed (Took {}ms to execute).", 
						entry.getValue().getTimedJob().getClass().getName(), System.currentTimeMillis() - startMoment);
			}
		}
	}
}
