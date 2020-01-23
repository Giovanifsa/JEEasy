package com.jeeasy.engine;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationPath("api")
public class JEEasyApplication extends Application {
	private Logger logger = LogManager.getLogger(JEEasyApplication.class);
	
	@PostConstruct
	public void postConstruct() {
		logger.info("JEEasy ONLINE");
	}
}
