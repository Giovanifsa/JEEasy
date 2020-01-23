package com.jeeasy.engine.utils.data.models;

public interface IOInterface<I, O> {
	public O output(I input);
}
