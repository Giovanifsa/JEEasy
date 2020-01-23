package com.jeeasy.engine.utils.containers;

/**
 * A Pair. Holds a typed pair of objects.
 * Can be used to return two values in the same method.
 * 
 * @author giovani-pc
 *
 * @param <L> Left object type
 * @param <R> Right object type
 */
public class Pair<L, R> {
	private L leftObject;
	private R rightObject;
	
	public static <L, R> Pair<L, R> from(L leftObject, R rightObject) {
		return new Pair<L, R>(leftObject, rightObject);
	}
	
	public Pair(L leftObject, R rightObject) {
		this.leftObject = leftObject;
		this.rightObject = rightObject;
	}
	
	public Pair() {
		
	}

	public L getLeftObject() {
		return leftObject;
	}

	public void setLeftObject(L leftObject) {
		this.leftObject = leftObject;
	}

	public R getRightObject() {
		return rightObject;
	}

	public void setRightObject(R rightObject) {
		this.rightObject = rightObject;
	}
}
