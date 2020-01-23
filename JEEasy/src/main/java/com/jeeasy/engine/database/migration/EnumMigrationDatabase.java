package com.jeeasy.engine.database.migration;

public enum EnumMigrationDatabase {
	MYSQL("MySQL"),
	POSTGRESQL("PostgreSQL")
	;
	
	private String databaseName;
	
	private EnumMigrationDatabase(String databaseName) {
		this.databaseName = databaseName;
	}
	
	public String getDatabaseName() {
		return databaseName;
	}
}
