package com.jeeasy.engine.resources.rest;

import javax.ws.rs.Path;

import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.queries.vos.UserVO;
import com.jeeasy.engine.resources.dtos.UserDTO;
import com.jeeasy.engine.services.UserService;

@Path(UserResource.PATH)
public class UserResource extends CRUDResource<User, UserDTO, UserVO, UserService> {
	public static final String PATH = "/user";
}
