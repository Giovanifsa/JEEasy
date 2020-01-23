package com.jeeasy.engine.configuration.database;

import java.nio.charset.Charset;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeeasy.engine.configuration.IConfigurator;
import com.jeeasy.engine.database.builders.UserBuilder;
import com.jeeasy.engine.database.eaos.UserEAO;
import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.database.entities.constants.UserRoleConstants;
import com.jeeasy.engine.settings.SystemCharsetSetting;
import com.jeeasy.engine.settings.SystemUserDefaultPasswordSetting;
import com.jeeasy.engine.utils.data.EncodingUtil;
import com.jeeasy.engine.utils.data.HashingUtil;

public class ConfigureSystemUser implements IConfigurator {
	@Inject
	private UserEAO userEAO;
	
	@Inject
	private SystemUserDefaultPasswordSetting systemUserDefaultPassword;
	
	@Inject
	private SystemCharsetSetting systemCharset;
	
	private Logger logger = LogManager.getLogger(ConfigureSystemUser.class);
	
	@Override
	public void configure() {
		User admin = userEAO.findByUserName("system");
		
		if (admin == null) {
			String password = systemUserDefaultPassword.getValue();
			Charset charset = systemCharset.getCharset();
			byte[] hashedPass = HashingUtil.applySHA512(password.getBytes(charset));
			String base64SHA512Password = EncodingUtil.applyBase64EncodeToString(hashedPass, charset);
			
			admin = new UserBuilder()
					.setUserName("system")
					.setName("System")
					.setBase64SHA512Password(base64SHA512Password)
					.addUserRole(UserRoleConstants.ROOT)
					.setSystemUser(true)
					.build();
			
			userEAO.persist(admin);
			
			logger.info("System user configured. Password: {} ({})", password, base64SHA512Password);
		}
		
		else {
			logger.info("System user already configured ({})", admin.getBase64SHA512Password());
		}
	}
}
