package com.jeeasy.engine.queries.vos;

public abstract class AbstractVO {
	public boolean constainsId() {
		return getId() != null;
	}
	
	public abstract Long getId();
}
