package com.jeeasy.engine.queries.vos;

public class UserVO extends AbstractVO{
	private Long idUser;
	private String userName;
	
	@Override
	public Long getId() {
		return getIdUser();
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
