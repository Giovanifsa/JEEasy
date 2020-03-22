package com.jeeasy.engine.settings;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeeasy.engine.exceptions.FailureRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumFailureExceptionCodes;
import com.jeeasy.engine.translations.EnumFailuresTranslations;
import com.jeeasy.engine.translations.Translator;

@Startup
@Singleton
public class SettingsManager {
	private String configPath = "." + File.separatorChar + "settings.cfg";
	private Path confPath = Paths.get(configPath);
	
	@Inject
	private Translator translator;
	
	@Inject
	private Instance<AbstractSetting<?>> settingsInstances;
	
	private ArrayList<AbstractSetting<?>> settingsList = new ArrayList<>();
	
	private byte[] lastConfigMD5 = null;
	
	private Logger logger = LogManager.getLogger(SettingsManager.class);
	
	@PostConstruct
	private void postConstruct() {
		loadSettingsBeans();
	}
	
	public void loadSettingsIfConfigFileChanged() {
		if (Files.exists(confPath)) {
			try {
				if (lastConfigMD5 != null) {
					byte[] currentConfigMD5 = getSettingsFileMD5();
					
					if (!Arrays.equals(currentConfigMD5, lastConfigMD5)) {
						logger.info("Changes detected in settings file. Reloading...");
						loadSettingsConfigFile();
					}
				}
				
				else {
					loadSettingsConfigFile();
				}
			} catch (Exception e) {
				throw new FailureRuntimeException(translator.translateForContextLanguage(
						EnumFailuresTranslations.FAILED_TO_READ_SETTINGS_DATA, confPath.toAbsolutePath().toString()), 
						EnumFailureExceptionCodes.FAILED_TO_READ_SETTINGS_FILE);
			}
		}
	}
	
	public void loadSettingsConfigFile() {
		//loadSettingsBeans();
	}
	
	private void loadSettingsBeans() {
		settingsList.clear();
		
		for (AbstractSetting<?> setting : settingsInstances) {
			settingsList.add(setting);
		}
	}
	
	private byte[] getSettingsFileMD5() throws Exception {
		byte[] settingFile = Files.readAllBytes(confPath);
		MessageDigest digester = MessageDigest.getInstance("MD5");
		
		return digester.digest(settingFile);
	}
	
	@Produces
	public AbstractSetting produceSetting(InjectionPoint ip) {
		return settingsList.stream()
				.filter(
						b -> ((Type) b.getClass()).equals(ip.getType()))
				.findAny().orElse(null);
	}
}