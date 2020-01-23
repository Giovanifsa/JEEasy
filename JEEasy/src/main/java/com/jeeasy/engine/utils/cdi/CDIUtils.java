package com.jeeasy.engine.utils.cdi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;

import com.jeeasy.engine.utils.containers.Pair;

public class CDIUtils {
	public static <E> E inject(Class<E> beanClass) {
		return CDI.current().select(beanClass).get();
	}
	
	@SafeVarargs
	public static <E> List<E> inject(Class<E>... beanClasses) {
		return inject(Arrays.asList(beanClasses));
	}
	
	public static <E> List<E> inject(List<Class<E>> beanClasses) {
		List<E> instances = new ArrayList<>();
		
		for (Class<E> clss : beanClasses) {
			instances.add(inject(clss));
		}
		
		return instances;
	}
	
	public static <E> Instance<E> instances(Class<E> genericSuperclass) {
		return CDI.current().select(genericSuperclass);
	}
	
	public static <E> void destroy(Class<E> beanClass, E beanToDestroy) {
		CDI.current().select(beanClass).destroy(beanToDestroy);
	}
	
	@SafeVarargs
	public static <E> void destroy(Pair<Class<E>, E>... beansToDestroy) {
		destroy(Arrays.asList(beansToDestroy));
	}
	
	public static <E> void destroy(List<Pair<Class<E>, E>> beansToDestroy) {
		for (Pair<Class<E>, E> bean : beansToDestroy) {
			destroy(bean.getLeftObject(), bean.getRightObject());
		}
	}
}
