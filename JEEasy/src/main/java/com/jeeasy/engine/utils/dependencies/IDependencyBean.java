package com.jeeasy.engine.utils.dependencies;

import java.util.List;

public interface IDependencyBean {
	public default List<Class<? extends IDependencyBean>> getDependencies() {
		return null;
	}
}
