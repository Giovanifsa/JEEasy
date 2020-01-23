package com.jeeasy.engine.queries;

import java.util.ArrayList;
import java.util.Iterator;

import com.jeeasy.engine.queries.vos.AbstractVO;

public class PagedResultList<V extends AbstractVO> implements Iterable<V>{
	private Long numberOfResults;
	private ArrayList<V> results = new ArrayList<>();
	
	public Long getNumberOfResults() {
		return numberOfResults;
	}
	
	public void setNumberOfResults(Long numberOfResults) {
		this.numberOfResults = numberOfResults;
	}
	
	public ArrayList<V> getResults() {
		return results;
	}
	
	public void setResults(ArrayList<V> results) {
		this.results = results;
	}
	
	public void addResult(V item) {
		results.add(item);
	}

	@Override
	public Iterator<V> iterator() {
		return results.iterator();
	}
}
