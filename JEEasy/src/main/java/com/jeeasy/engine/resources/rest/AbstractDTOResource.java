package com.jeeasy.engine.resources.rest;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.resources.dtos.AbstractDTO;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public abstract class AbstractDTOResource<E extends AbstractEntity, D extends AbstractDTO<E>> extends AbstractResource {
	@Inject
	private Instance<D> dtoBuilder;
	
	public E convertDTOToEntity(D dto) {
		if (dto != null) {
			return dto.convertDTOtoEntity();
		}
		
		return null;
	}
	
	public D convertEntityToDTO(E entity) {
		if (entity != null) {
			D dto = newDTO();
			dto.readFromEntity(entity);
			
			return dto;
		}
		
		return null;
	}
	
	public D newDTO() {
		return dtoBuilder.get();
	}
}
