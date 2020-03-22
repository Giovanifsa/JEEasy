package com.jeeasy.engine.database.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User extends AbstractEntity {
	public static final String ENTITY_USER = User.class.getSimpleName();
	
	public static final String ATTRIBUTE_IDUSER = "idUser";
	public static final String ATTRIBUTE_USERROLES = "userRoles";
	public static final String ATTRIBUTE_USERNAME = "userName";
	public static final String ATTRIBUTE_BASE64SHA512PASSWORD = "base64SHA512Password";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	
	@Column(nullable = false, length = 64)
	private String userName;
	
	@Column(nullable = false, length = 256)
	private String base64SHA512Password;
	
	@Column(nullable = false, length = 64)
	private String name;
	
	@Column(name = "systemUser")
	private Boolean systemUser;
	
	@ManyToMany
	@JoinTable(
			name = "UserMMUserRole", 
			joinColumns = {
					@JoinColumn(name = "idUser")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "idUserRole")
			}
	)
	private List<UserRole> userRoles;

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

	public String getBase64SHA512Password() {
		return base64SHA512Password;
	}

	public void setBase64SHA512Password(String base64sha512Password) {
		base64SHA512Password = base64sha512Password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean isSystemUser() {
		return systemUser;
	}

	public void setSystemUser(Boolean systemUser) {
		this.systemUser = systemUser;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Boolean getSystemUser() {
		return systemUser;
	}

	@Override
	public Long getId() {
		return getIdUser();
	}
}
