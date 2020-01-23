package com.jeeasy.engine.database.entities;

public abstract class AbstractEntity {
	public boolean containsId() {
		return getId() != null;
	}
	
	public abstract Long getId();
	
	@Override
	public boolean equals(Object obj) {
		return (obj != null &&
				obj instanceof AbstractEntity &&
				obj.getClass().isAssignableFrom(getClass()) && 
				((AbstractEntity) obj).getId().equals(getId()));
	}
	
	@Override
	public int hashCode() {
		String toHash = getClass().getSimpleName() + "[" + getId() + "]";
		int hash = 0;
		
		for (char c : toHash.toCharArray()) {
			hash += (c % toHash.length()) % (hash + 1);
		}
		
		return hash;
	}
	
	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + " ID: " + getId() + "]";
	}
}
