package com.jeeasy.engine.database.eaos;

import javax.persistence.TypedQuery;

import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.queries.PagedResultList;
import com.jeeasy.engine.queries.QuerySettings;
import com.jeeasy.engine.queries.UserDefaultQuery;
import com.jeeasy.engine.queries.vos.UserVO;

public class UserEAO extends AbstractListingEAO<User, UserVO> {
	public User findByUserLogin(String userName, String base64SHA512Password) {
		StringBuilder sb = new StringBuilder();
		sb.append("	SELECT su ");
		sb.append(" FROM ").append(User.ENTITY_USER).append(" su ");
		sb.append(" WHERE su.").append(User.ATTRIBUTE_USERNAME).append(" = :userName ");
		sb.append(" AND su.").append(User.ATTRIBUTE_BASE64SHA512PASSWORD).append(" = :base64SHA512Password");
		
		TypedQuery<User> query = createTypedQuery(sb.toString());
		query.setParameter("userName", userName);
		query.setParameter("base64SHA512Password", base64SHA512Password);
		
		return getSingleResult(query);
	}
	
	public User findByUserName(String userName) {
		StringBuilder sb = new StringBuilder();
		sb.append("	SELECT su ");
		sb.append(" FROM ").append(User.ENTITY_USER).append(" su ");
		sb.append(" WHERE su.").append(User.ATTRIBUTE_USERNAME).append(" = :userName ");
		
		TypedQuery<User> query = createTypedQuery(sb.toString());
		query.setParameter("userName", userName);
		
		return getSingleResult(query);
	}

	@Override
	public PagedResultList<UserVO> getDefaultPagedResultList(QuerySettings querySettings) {
		return getPagedResultList(new UserDefaultQuery(), querySettings);
	}
}
