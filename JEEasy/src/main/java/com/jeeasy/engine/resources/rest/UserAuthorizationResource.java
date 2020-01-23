package com.jeeasy.engine.resources.rest;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.jeeasy.engine.database.entities.UserAuthorization;
import com.jeeasy.engine.resources.dtos.UserAuthorizationDTO;
import com.jeeasy.engine.resources.dtos.beans.UserLoginBean;
import com.jeeasy.engine.services.UserAuthorizationService;

@Path(UserAuthorizationResource.PATH)
public class UserAuthorizationResource extends AbstractDTOResource<UserAuthorization, UserAuthorizationDTO> {
	public static final String PATH = "/systemuserauthorization";
	
	@Inject
	private UserAuthorizationService service;

	@POST
	@Path("/login")
	public UserAuthorizationDTO login(UserLoginBean userLoginBean) {
		return convertEntityToDTO(service.login(userLoginBean));
	}
	
	@POST
	@Path("/renew")
	public UserAuthorizationDTO renewAuthorization() {
		return convertEntityToDTO(service.renewAuthorization());
	}
	
	@POST
	@Path("/logout")
	public void logout() {
		service.logout();
	}
	
	@POST
	@Path("/cleanauths")
	public void cleanAuthorizations(UserLoginBean userLoginBean) {
		service.cleanUserAuthorizations(userLoginBean);
	}
}
