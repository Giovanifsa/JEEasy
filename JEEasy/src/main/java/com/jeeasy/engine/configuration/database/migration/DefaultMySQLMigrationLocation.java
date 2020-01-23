package com.jeeasy.engine.configuration.database.migration;

import com.jeeasy.engine.database.migration.EnumMigrationDatabase;
import com.jeeasy.engine.database.migration.IMigrationLocation;

public class DefaultMySQLMigrationLocation implements IMigrationLocation {
	@Override
	public String getLocation() {
		return "sql/mysql";
	}

	@Override
	public EnumMigrationDatabase getSupportedDatabase() {
		return EnumMigrationDatabase.MYSQL;
	}
}
