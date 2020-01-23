package com.jeeasy.engine.utils.reflection;

import java.util.ArrayList;
import java.util.List;

import com.jeeasy.engine.utils.dependencies.IDependencyBean;

public final class ClassUtils {
	@SuppressWarnings("unchecked")
	public static <T> List<Class<T>> filterClassesInstancesOf(Class<T> filter, List<Class<?>> classesToFilter) {
		ArrayList<Class<T>> filteredClasses = new ArrayList<>();
		
		for (Class<?> classIterator : classesToFilter) {
			if (filter.isAssignableFrom(classIterator)) {
				filteredClasses.add((Class<T>) classIterator);
			}
		}
		
		return filteredClasses;
	}
	
	public static List<Class<?>> castToGenerics(List<Class<? extends IDependencyBean>> dependencyBeanClasses) {
		ArrayList<Class<?>> castedClasses = new ArrayList<>();
		
		dependencyBeanClasses.forEach((c) -> {
			castedClasses.add((Class<?>) c);
		});
		
		return castedClasses;
	}
}
