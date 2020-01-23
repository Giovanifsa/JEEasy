package com.jeeasy.engine.resources.dtos;

import com.jeeasy.engine.database.entities.AbstractEntity;

public abstract class AbstractDTO<E extends AbstractEntity> {
	public boolean containsId() {
		return getId() != null;
	}
	
	public abstract void readFromEntity(E entity);
	
	public abstract E convertDTOtoEntity();
	
	public abstract Long getId();
}
