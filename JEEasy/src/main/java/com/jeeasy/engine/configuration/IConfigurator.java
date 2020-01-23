package com.jeeasy.engine.configuration;

import com.jeeasy.engine.utils.dependencies.IDependencyBean;

public interface IConfigurator extends IDependencyBean {
	public abstract void configure();
}
