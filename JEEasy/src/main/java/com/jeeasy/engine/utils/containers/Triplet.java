package com.jeeasy.engine.utils.containers;

/**
 * A Triplet. Holds three typed objects.
 * Can be used to return three objects in the same method.
 * 
 * @author giovani-pc
 *
 * @param <L> Left object type
 * @param <M> Middle object type
 * @param <R> Right object type
 */
public class Triplet<L, M, R> {
	private L leftObject;
	private M middleObject;
	private R rightObject;
	
	public static <L, M, R> Triplet<L, M, R> from(L leftObject, M middleObject, R rightObject) {
		return new Triplet<L, M, R>(leftObject, middleObject, rightObject);
	}
	
	public Triplet(L leftObject, M middleObject, R rightObject) {
		this.leftObject = leftObject;
		this.middleObject = middleObject;
		this.rightObject = rightObject;
	}
	
	public Triplet() {
		
	}
	
	public L getLeftObject() {
		return leftObject;
	}
	
	public void setLeftObject(L leftObject) {
		this.leftObject = leftObject;
	}
	
	public M getMiddleObject() {
		return middleObject;
	}
	
	public void setMiddleObject(M middleObject) {
		this.middleObject = middleObject;
	}
	
	public R getRightObject() {
		return rightObject;
	}
	
	public void setRightObject(R rightObject) {
		this.rightObject = rightObject;
	}
}
