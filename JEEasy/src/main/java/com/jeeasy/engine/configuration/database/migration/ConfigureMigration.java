package com.jeeasy.engine.configuration.database.migration;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import com.jeeasy.engine.database.migration.IMigrationLocation;
import com.jeeasy.engine.exceptions.FailureRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumFailureExceptionCodes;
import com.jeeasy.engine.translations.EnumFailuresTranslations;
import com.jeeasy.engine.translations.Translator;
import com.jeeasy.engine.utils.dependencies.IDependencyBean;

/**
 * This is the most basic configuration bean.
 * This configuration is required by the entire architecture, 
 * as it prepares the database for usage.
 * 
 * Any other configuration should run as a separate bean,
 * and must depend on the completion of this one (@DependsOn).
 * 
 * @author giovani
 *
 */

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class ConfigureMigration implements IDependencyBean {
	public static final String ConfigureMigrationName = "ConfigureMigration";
	
	@Resource(lookup = "java:/JEEasy/JEEasyDS")
	private DataSource dataSource;
	
	@Inject
	private Instance<IMigrationLocation> migrationLocations;
	
	@Inject
	private Translator translator;
	
	private Logger logger = LogManager.getLogger(ConfigureMigration.class);
	
	@PostConstruct
	public void configure() {
		if (dataSource != null) {
			try {
				logger.info("Starting Database Migration...");
				FluentConfiguration fluentConfiguration = new FluentConfiguration();
				
				fluentConfiguration.dataSource(dataSource);
				fluentConfiguration.baselineOnMigrate(true);
				fluentConfiguration.outOfOrder(true);
				fluentConfiguration.batch(true);
				
				ArrayList<String> locations = new ArrayList<>();
				
				for (IMigrationLocation location : migrationLocations) {
					logger.info("Registered SQL resource location \"{}\" for {}.", 
							location.getLocation(), 
							location.getSupportedDatabase().getDatabaseName());
					
					locations.add(location.getLocation());
				}
				
				String[] locationsArray = new String[locations.size()];
				locations.toArray(locationsArray);
				
				fluentConfiguration.locations(locationsArray);
				
				Flyway flyway = fluentConfiguration.load();
				
				logger.info("Migrating database...");
				flyway.migrate();
				
				logger.info("Migration finished.");
			} catch (Exception ex) {
				throw new FailureRuntimeException(translator.translateForContextLanguage(
						EnumFailuresTranslations.FAILED_TO_APPLY_MIGRATION, ex.getMessage()),
						EnumFailureExceptionCodes.FAILED_TO_APPLY_MIGRATION);
			}
		}
		
		else {
			throw new FailureRuntimeException(translator.translateForContextLanguage(
					EnumFailuresTranslations.FAILED_TO_APPLY_MIGRATION_DATASOURCE_NOT_FOUND),
					EnumFailureExceptionCodes.FAILED_TO_APPLY_MIGRATION);
		}
	}
}
