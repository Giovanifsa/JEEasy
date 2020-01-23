package com.jeeasy.engine.utils.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class ReflectionUtils {
	public static Method findGetterForField(Class<?> getterClass, Field field) {
		for (Method method : getterClass.getMethods()) {
			if (method.getName().toLowerCase().equals("get" + field.getName().toLowerCase())
					&& method.getParameterCount() == 0) {
				return method;
			}
		}

		return null;
	}

	public static Method findSetterForField(Class<?> setterClass, Field field) {
		for (Method method : setterClass.getMethods()) {
			if (method.getName().toLowerCase().equals("set" + field.getName().toLowerCase())
					&& method.getParameterCount() == 0) {
				return method;
			}
		}

		return null;
	}

	public static <T> Constructor<T> findEmptyConstructorForClass(Class<T> searchClass) {
		try {
			return searchClass.getConstructor();
		} catch (NoSuchMethodException ex) {
			return null;
		}
	}

	public static Class<?> getGenericType(Object instance, int position) {
		return ((Class<?>) ((ParameterizedType) instance.getClass().getGenericSuperclass()).getActualTypeArguments()[position]);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getExpectedGenericType(Object instance, Class<T> expected, int position) {
		return ((Class<T>) ((ParameterizedType) instance.getClass().getGenericSuperclass()).getActualTypeArguments()[position]);
	}
}
