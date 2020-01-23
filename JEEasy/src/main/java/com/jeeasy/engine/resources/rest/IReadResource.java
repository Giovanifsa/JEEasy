package com.jeeasy.engine.resources.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.resources.dtos.AbstractDTO;

public interface IReadResource<E extends AbstractEntity, D extends AbstractDTO<E>> {
	@GET
	@Path("/{id}")
	D find(@PathParam("id") Long id);
}
