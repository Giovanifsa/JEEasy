package com.jeeasy.engine.utils.data;

import com.jeeasy.engine.utils.data.models.IOInterface;

public final class ArrayUtils {
	@SuppressWarnings("unchecked")
	public static <I, O> O[] convertArray(I[] array, IOInterface<I, O> converter) {
		Object[] convertedItems = new Object[array.length];
		
		for (int i = 0; i < array.length; i++) {
			convertedItems[i] = converter.output(array[i]);
		}
		
		return (O[]) convertedItems;
	}
}
