package com.jeeasy.engine.resources.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface IDeleteResource {
	@DELETE
	@Path("/{id}")
	void delete(@PathParam("id") Long id);
}
