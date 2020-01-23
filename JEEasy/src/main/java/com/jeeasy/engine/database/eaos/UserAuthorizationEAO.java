package com.jeeasy.engine.database.eaos;

import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.database.entities.UserAuthorization;
import com.jeeasy.engine.queries.PagedResultList;
import com.jeeasy.engine.queries.QuerySettings;
import com.jeeasy.engine.queries.vos.UserAuthorizationVO;

public class UserAuthorizationEAO extends AbstractListingEAO<UserAuthorization, UserAuthorizationVO> {
	
	public UserAuthorization findByAuthorization(String authorization) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("		sysauth ");
		sb.append(" FROM ").append(UserAuthorization.ENTITY_USERAUTHORIZATION).append(" sysauth ");
		sb.append(" WHERE ");
		sb.append("		sysauth.").append(UserAuthorization.ATTRIBUTE_AUTHORIZATION).append(" = :authorization ");
		
		TypedQuery<UserAuthorization> query = createTypedQuery(sb.toString());
		query.setParameter("authorization", authorization);
		
		return getSingleResult(query);
	}

	@Override
	public PagedResultList<UserAuthorizationVO> getDefaultPagedResultList(QuerySettings querySettings) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Long countAuthorizationsForUser(User user) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("		COUNT(ua) ");
		sb.append("	FROM ").append(UserAuthorization.ENTITY_USERAUTHORIZATION).append(" ua ");
		sb.append(" WHERE ");
		sb.append("		ua.").append(UserAuthorization.ATTRIBUTE_USER).append(" = :user ");
		
		TypedQuery<Long> query = createTypedQuery(sb.toString(), Long.class);
		query.setParameter("user", user);
		
		return getSingleResult(query);
	}
	
	public void deleteAllUserAuthorizationTokens(User user) {
		StringBuilder sb = new StringBuilder();
		sb.append(" DELETE ");
		sb.append(" FROM ").append(UserAuthorization.ENTITY_USERAUTHORIZATION).append(" ua ");
		sb.append(" WHERE ");
		sb.append("		ua.").append(UserAuthorization.ATTRIBUTE_USER).append(" = :user ");
		
		Query query = createQuery(sb.toString());
		query.setParameter("user", user);
		
		query.executeUpdate();
	}
	
	public void deleteExpiredAuthorizations() {
		StringBuilder sb = new StringBuilder();
		sb.append(" DELETE ");
		sb.append(" FROM ");
		sb.append(" ").append(UserAuthorization.ENTITY_USERAUTHORIZATION).append(" ua ");
		sb.append(" WHERE ua.").append(UserAuthorization.ATTRIBUTE_EXPIRATIONDATE).append(" < :curDate ");
		
		Query query = createQuery(sb.toString());
		query.setParameter("curDate", new Date());
		
		query.executeUpdate();
	}
}
