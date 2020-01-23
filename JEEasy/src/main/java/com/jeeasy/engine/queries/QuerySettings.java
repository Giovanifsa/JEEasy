package com.jeeasy.engine.queries;

import java.util.List;

public class QuerySettings {
	private int resultLimit;
	private Long offsetId;
	private String search;
	private List<String> filteringColumns;
	private String columnToOrder;
	private String orderMethod;
	
	public QuerySettings(int resultLimit, Long offsetId, String search, List<String> filteringColumns, String columnToOrder, String orderMethod) {
		this.resultLimit = resultLimit;
		this.offsetId = offsetId;
		this.search = search;
		this.filteringColumns = filteringColumns;
		this.columnToOrder = columnToOrder;
		this.orderMethod = orderMethod;
	}
	
	public QuerySettings() {
		
	}

	public int getResultLimit() {
		return resultLimit;
	}
	
	public void setResultLimit(int resultLimit) {
		this.resultLimit = resultLimit;
	}
	
	public Long getOffsetId() {
		return offsetId;
	}
	
	public void setOffsetId(Long offsetId) {
		this.offsetId = offsetId;
	}
	
	public List<String> getFilteringColumns() {
		return filteringColumns;
	}
	
	public void setFilteringColumns(List<String> filteringColumns) {
		this.filteringColumns = filteringColumns;
	}
	
	public String getColumnToOrder() {
		return columnToOrder;
	}
	
	public void setColumnToOrder(String columnToOrder) {
		this.columnToOrder = columnToOrder;
	}
	
	public String getOrderMethod() {
		return orderMethod;
	}
	
	public void setOrderMethod(String orderMethod) {
		this.orderMethod = orderMethod;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
