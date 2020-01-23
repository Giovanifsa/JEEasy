package com.jeeasy.engine.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.queries.vos.UserVO;
import com.jeeasy.engine.queries.vos.builders.UserVOBuilder;

public class UserDefaultQuery extends AbstractViewObjectQuery<UserVO> {

	@Override
	public String getQuery() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT ");
		sb.append(		User.ATTRIBUTE_USERNAME + ", ");
		sb.append(		User.ATTRIBUTE_IDUSER);
		sb.append("	FROM " + User.ENTITY_USER + " ");
		
		return sb.toString();
	}

	@Override
	public String getIdColumnName() {
		return User.ATTRIBUTE_IDUSER;
	}

	@Override
	public UserVO getVOFromResultSetRow(ResultSet rs) throws SQLException {
		return 
			new UserVOBuilder()
			.setIdSystemUser(rs.getLong(User.ATTRIBUTE_IDUSER))
			.setUserName(rs.getString(User.ATTRIBUTE_USERNAME))
			.build();
	}
}
