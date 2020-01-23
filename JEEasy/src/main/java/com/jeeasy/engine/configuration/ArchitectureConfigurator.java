package com.jeeasy.engine.configuration;


import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeeasy.engine.configuration.database.migration.ConfigureMigration;
import com.jeeasy.engine.utils.cdi.CDIUtils;
import com.jeeasy.engine.utils.dependencies.DependencyBasedConfiguratorUtil;
import com.jeeasy.engine.utils.dependencies.IDependencyBean;

@Singleton
@Startup
@DependsOn(ConfigureMigration.ConfigureMigrationName)
public class ArchitectureConfigurator {
	public static final String ArchitectureConfiguratorName = "ArchitectureConfigurator";
	
	private Logger logger = LogManager.getLogger(ArchitectureConfigurator.class);
	
	@PostConstruct
	public void postConstruct() {
		reconfigure();
	}
	
	public void reconfigure() {
		logger.info("Initializing setup configuration...");
		Long configurationStartMoment = System.currentTimeMillis();
		
		DependencyBasedConfiguratorUtil dependencyConfiguratorUtil = new DependencyBasedConfiguratorUtil(IConfigurator.class);
		
		Class<? extends IDependencyBean> unconfiguredClass = null;
		
		while ((unconfiguredClass = dependencyConfiguratorUtil.getNextUnconfigured()) != null) {
			IConfigurator configurator = (IConfigurator) CDIUtils.inject(unconfiguredClass);
			
			Long startMoment = System.currentTimeMillis();
			logger.info("Running configurator {}...", unconfiguredClass.getName());
			
			configurator.configure();
			
			logger.info("Took ~{}ms configurating \"{}\".", System.currentTimeMillis() - startMoment, unconfiguredClass.getName());
			
			CDIUtils.destroy(IConfigurator.class, configurator);
			dependencyConfiguratorUtil.markConfigured(unconfiguredClass);
		}
		
		logger.info("Configuration finished (took ~{}ms configurating).", System.currentTimeMillis() - configurationStartMoment);
	}
}
