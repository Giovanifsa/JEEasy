package com.jeeasy.engine.database.migration;

public interface IMigrationLocation {
	public EnumMigrationDatabase getSupportedDatabase();
	public String getLocation();
}
