package com.jeeasy.engine.utils.containers;

/**
 * A Unit. A unique typed object container.
 * It's purpose is to hold a unique pointer.
 * 
 * Can be used as a reference holder.
 * 
 * @author giovani-pc
 *
 * @param <T> Unique object type
 */
public class Unit<T> {
	private T object;
	
	public static <T> Unit<T> from(T object) {
		return new Unit<T>(object);
	}
	
	public Unit(T object) {
		this.object = object;
	}
	
	public Unit() {
		
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
}
