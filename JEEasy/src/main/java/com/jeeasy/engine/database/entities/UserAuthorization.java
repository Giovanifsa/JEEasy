package com.jeeasy.engine.database.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UserAuthorization extends AbstractEntity {
	public static final String ENTITY_USERAUTHORIZATION = UserAuthorization.class.getSimpleName();
	
	public static final String ATTRIBUTE_AUTHORIZATION = "authorization";
	public static final String ATTRIBUTE_EXPIRATIONDATE = "expirationDate";
	public static final String ATTRIBUTE_USER = "user";
	
	public static final String COLUMN_IDUSER = "idUser";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUserAuthorization;
	
	@JoinColumn(name = "idUser", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	private String authorization;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;
	
	public Long getIdUserAuthorization() {
		return idUserAuthorization;
	}

	public void setIdUserAuthorization(Long idUserAuthorization) {
		this.idUserAuthorization = idUserAuthorization;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public boolean isExpired(Date currentDate) {
		return expirationDate.compareTo(currentDate) < 0;
	}

	@Override
	public Long getId() {
		return getIdUserAuthorization();
	}
}
