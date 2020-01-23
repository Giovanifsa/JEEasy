package com.jeeasy.engine.resources.rest;

import java.util.List;

import javax.inject.Inject;

import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.queries.PagedResultList;
import com.jeeasy.engine.queries.QuerySettings;
import com.jeeasy.engine.queries.vos.AbstractVO;
import com.jeeasy.engine.resources.dtos.AbstractDTO;
import com.jeeasy.engine.services.AbstractService;


public abstract class CRUDResource<E extends AbstractEntity, D extends AbstractDTO<E>, V extends AbstractVO, 
								   S extends AbstractService<E, V, ?, ?>> extends AbstractDTOResource<E, D>  
									implements IWriteResource<E, D>, IReadResource<E, D>, IDeleteResource, IPagedReadResource<V> {
	@Inject
	private S service;
	
	@Override
	public D save(D incomming) {
		return convertEntityToDTO(service.save(convertDTOToEntity(incomming)));
	}

	@Override
	public D find(Long id) {
		return convertEntityToDTO(service.find(id));
	}
	
	@Override
	public void delete(Long id) {
		service.delete(id);
	}
	
	@Override
	public PagedResultList<V> defaultPagedSearch(int resultLimit, Long offsetId, String search,
					List<String> filteringColumns, String columnToOrder, String orderMethod) {
		QuerySettings querySettings = new QuerySettings(resultLimit, offsetId, search, filteringColumns, columnToOrder, orderMethod);
		return service.defaultPagedSearch(querySettings);
	}

	public S getService() {
		return service;
	}
}
