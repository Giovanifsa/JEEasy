package com.jeeasy.engine.queries.vos.builders;

import com.jeeasy.engine.queries.vos.UserVO;
import com.jeeasy.engine.utils.data.models.Builder;

public class UserVOBuilder implements Builder<UserVO> {
	private UserVO sysUser = new UserVO();
	
	public UserVOBuilder setIdSystemUser(Long idSystemUser) {
		sysUser.setIdUser(idSystemUser);
		return this;
	}

	public UserVOBuilder setUserName(String userName) {
		sysUser.setUserName(userName);
		return this;
	}

	@Override
	public UserVO build() {
		return sysUser;
	}
}