package com.jeeasy.engine.database.entities.constants;

import java.util.List;

import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.utils.dependencies.IDependencyBean;

public interface IEntityConstants extends IDependencyBean {
	public List<AbstractEntity> getConstantEntities();
}
