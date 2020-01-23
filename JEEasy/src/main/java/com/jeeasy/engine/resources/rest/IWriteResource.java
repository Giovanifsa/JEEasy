package com.jeeasy.engine.resources.rest;

import javax.ws.rs.POST;

import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.resources.dtos.AbstractDTO;

public interface IWriteResource<E extends AbstractEntity, D extends AbstractDTO<E>> {
	@POST
	public D save(D incomming);
}
