package com.jeeasy.engine.configuration.database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeeasy.engine.configuration.IConfigurator;
import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.database.entities.constants.IEntityConstants;
import com.jeeasy.engine.utils.cdi.CDIUtils;
import com.jeeasy.engine.utils.dependencies.DependencyBasedConfiguratorUtil;
import com.jeeasy.engine.utils.dependencies.IDependencyBean;

public class ConfigureConstantEntities implements IConfigurator {
	@PersistenceContext
	private EntityManager entityManager;
	
	private Logger logger = LogManager.getLogger(ConfigureConstantEntities.class);

	@Override
	public void configure() {
		DependencyBasedConfiguratorUtil dependencyConfiguratorUtil = new DependencyBasedConfiguratorUtil(IEntityConstants.class);
		
		Class<? extends IDependencyBean> unconfiguredConstantClass = null;
		
		while ((unconfiguredConstantClass = dependencyConfiguratorUtil.getNextUnconfigured()) != null) {
			IEntityConstants entityConstants = (IEntityConstants) CDIUtils.inject(unconfiguredConstantClass);
			
			logger.info("Persisting constant values from \"{}\"...", unconfiguredConstantClass.getName());
			
			for (AbstractEntity entity : entityConstants.getConstantEntities()) {
				entityManager.merge(entity);
				logger.info("Persisted constant \"{}\"", entity.toString());
			}
			
			CDIUtils.destroy(IEntityConstants.class, entityConstants);
			dependencyConfiguratorUtil.markConfigured(unconfiguredConstantClass);
		}
	}
}
