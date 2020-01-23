package com.jeeasy.engine.database.eaos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.queries.AbstractViewObjectQuery;
import com.jeeasy.engine.queries.PagedResultList;
import com.jeeasy.engine.queries.QuerySettings;
import com.jeeasy.engine.queries.vos.AbstractVO;
import com.jeeasy.engine.utils.containers.Unit;
import com.jeeasy.engine.utils.data.StringUtils;

public abstract class AbstractListingEAO<E extends AbstractEntity, V extends AbstractVO> extends AbstractEAO<E> {
	public abstract PagedResultList<V> getDefaultPagedResultList(QuerySettings querySettings);
	
	protected PagedResultList<V> getPagedResultList(AbstractViewObjectQuery<V> query, QuerySettings querySettings, Object... params) {
		@SuppressWarnings("unchecked")
		PagedResultList<V> pagedResultList = (PagedResultList<V>) runPagedQuery(query, querySettings, false, params).getObject();
		pagedResultList.setNumberOfResults((Long) runPagedQuery(query, querySettings, false, params).getObject());
		
		return pagedResultList;
	}
	
	private Unit<Object> runPagedQuery(AbstractViewObjectQuery<V> query, QuerySettings querySettings, boolean counting, Object... params) {
		StringBuilder sb = new StringBuilder();
		String idColumnName = query.getIdColumnName();
		
		Long offsetId = querySettings.getOffsetId();
		String search = querySettings.getSearch();
		String orderColumn = querySettings.getColumnToOrder();
		String orderMethod = readOrderMethod(querySettings.getOrderMethod());
		int limit = readLimit(querySettings.getResultLimit());
		List<String> filterColumnList = querySettings.getFilteringColumns();
		
		sb.append(" SELECT ");
		
		ArrayList<String> columnList = prepareSelectColumnsForPagedSelect(sb, search, filterColumnList, counting);
		
		if (counting) {
			sb.append(" COUNT(*) AS __resultCounter ");
		}
		
		sb.append(" FROM (");
		sb.append(query.getQuery());
		sb.append(" ) queryResult ");
		
		sb.append(" WHERE ( ").append(idColumnName).append(" > ? ");
		
		prepareSearchConditionsForPagedSelect(sb, columnList);
		
		sb.append(" )");
		
		prepareOrdering(sb, orderColumn, idColumnName, orderMethod);
		
		if (!counting) {
			sb.append(" LIMIT ? ");
		}
		
		final Unit<Object> ref = new Unit<>();
		
		getHibernateSession().doWork((jdbcConnection) -> {
			PreparedStatement statement = jdbcConnection.prepareStatement(sb.toString());
			
			int iParam = 0;
			
			//Add inner query params
			for (; iParam < params.length; iParam++) {
				statement.setObject(iParam, params[iParam]);
			}
			
			//Add outer query params
			//Offset for paged list
			statement.setObject(++iParam, offsetId);
			
			//Search text
			
			for (int iSearchParam = 0; iSearchParam < columnList.size(); iSearchParam++) {
				statement.setObject(++iParam, search);
			}
			
			if (!counting) {
				statement.setObject(++iParam, limit);
			}
			
			if (counting) {
				ResultSet rs = statement.executeQuery();
				
				if (rs.next()) {
					ref.setObject(rs.getLong("__resultCounter"));
				}
				
				rs.close();
			}
			
			else {
				ResultSet rs = statement.executeQuery();
				ref.setObject(query.readResultSet(rs));
				
				rs.close();
			}
		});
		
		return ref;
	}
	
	private ArrayList<String> prepareSelectColumnsForPagedSelect(StringBuilder sb, String search, List<String> columns, boolean isCounting) {
		ArrayList<String> columnList = new ArrayList<>();
		
		if (StringUtils.isNonBlankString(search)) {
			String previousColumn = null;
			
			for (int iColumn = 0; iColumn < columns.size(); iColumn++) {				
				String column = columns.get(iColumn);
				
				if (StringUtils.isNonBlankString(column)) {
					if (!isCounting) {
						if (StringUtils.isNonBlankString(previousColumn)) {
							sb.append(" , ");
						}
						
						sb.append(column);
					}
					
					columnList.add(column);
				}
				
				previousColumn = column;
			}
		}
		
		else {
			if (!isCounting) {
				sb.append(" * ");
			}
		}
		
		return columnList;
	}
	
	private void prepareSearchConditionsForPagedSelect(StringBuilder sb, List<String> columnList) {
		if (!columnList.isEmpty()) {
			sb.append(" AND (");		
			
			for (int iColumn = 0; iColumn < columnList.size(); iColumn++) {
				String column = columnList.get(0);
				
				if (iColumn > 0) {
					sb.append(" OR ");
				}
				
				sb.append(" CAST(").append(column).append(" as CHAR) LIKE CONCAT('%', :search, '%') "); //TODO: Fix sql injection problem
			}
			
			sb.append(" )");
		}
	}
	
	private String readOrderMethod(String orderMethod) {
		if (StringUtils.isNonBlankString(orderMethod) && orderMethod.toLowerCase().trim().equals("asc")) {
			return "DESC";
		}
		
		return "ASC";
	}
	
	private void prepareOrdering(StringBuilder sb, String orderColumn, String idColumnName, String orderMethod) {
		if (StringUtils.isNonBlankString(orderColumn)) {
			sb.append(" ORDER BY ").append(orderColumn).append(" ").append(orderMethod); //TODO: Fix sql injection at this line
		}
		
		else {
			sb.append(" ORDER BY ").append(idColumnName).append(" ").append(orderMethod); //TODO: Fix sql injection at this line
		}
	}
	
	private int readLimit(int resultLimit) {
		int limit = resultLimit;
		
		if (limit <= 0) {
			limit = 10;
		}
		
		return limit;
	}
}