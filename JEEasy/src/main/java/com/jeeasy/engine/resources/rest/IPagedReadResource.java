package com.jeeasy.engine.resources.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.jeeasy.engine.queries.PagedResultList;
import com.jeeasy.engine.queries.vos.AbstractVO;

public interface IPagedReadResource<V extends AbstractVO> {
	@GET
	@Path("/paged")
	public PagedResultList<V> defaultPagedSearch(@QueryParam("resultLimit") int resultLimit,
												 @QueryParam("offset") Long offsetId,
												 @QueryParam("search") String search,
												 @QueryParam("columnsToFilter") List<String> filteringColumns,
												 @QueryParam("columnToOrder") String columnToOrder,
												 @QueryParam("orderMethod") String orderMethod);
}
