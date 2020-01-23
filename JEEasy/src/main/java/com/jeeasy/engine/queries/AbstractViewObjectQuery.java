package com.jeeasy.engine.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jeeasy.engine.queries.vos.AbstractVO;

public abstract class AbstractViewObjectQuery<V extends AbstractVO> {
	public abstract String getQuery();
	
	public abstract String getIdColumnName();
	
	public abstract V getVOFromResultSetRow(ResultSet rs) throws SQLException;
	
	public PagedResultList<V> readResultSet(ResultSet rs) throws SQLException {
		PagedResultList<V> paged = new PagedResultList<V>();
		
		while (rs.next()) {
			paged.addResult(getVOFromResultSetRow(rs));
		}
		
		return paged;
	}
}
