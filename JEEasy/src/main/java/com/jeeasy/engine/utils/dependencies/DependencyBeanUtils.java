package com.jeeasy.engine.utils.dependencies;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;

import com.jeeasy.engine.utils.cdi.CDIUtils;
import com.jeeasy.engine.utils.data.ListUtils;

public final class DependencyBeanUtils {
	private DependencyBeanUtils() {}
	
	public static List<Class<? extends IDependencyBean>> listAllDependencyBeansClasses() {
		List<Class<? extends IDependencyBean>> dependencyBeanClasses = new ArrayList<>();
		
		Instance<IDependencyBean> dependencyBeanInjector = CDIUtils.instances(IDependencyBean.class);
		
		for (IDependencyBean bean : dependencyBeanInjector) {
			dependencyBeanClasses.add(bean.getClass());
		}
		
		return dependencyBeanClasses;
	}
	
	/**
	 * Gets each dependency bean class that is a instance of filterClass.
	 * 
	 * @param filterClass
	 * @return
	 */
	public static <T extends IDependencyBean> List<Class<? extends IDependencyBean>> getDependencyBeans(Class<T> filterClass) {
		List<Class<? extends IDependencyBean>> dependencyBeansClasses = new ArrayList<>();
		
		Instance<T> dependencyBeanInjector = CDIUtils.instances(filterClass);
		
		for (T dependencyBeanInstance : dependencyBeanInjector) {
			dependencyBeansClasses.add(dependencyBeanInstance.getClass());
			dependencyBeanInjector.destroy(dependencyBeanInstance);
		}
		
		return filterEqualBeansClasses(dependencyBeansClasses);
	}
	
	/**
	 * Gets the class of each bean that depends direct on the dependingClass parameter.
	 * That is, the filter checks if the dependent has a dependency defined as dependingClass.
	 *  
	 * @param dependingClass
	 * @return List of dependent classes depending on dependingClass.
	 */
	public static List<Class<? extends IDependencyBean>> getDirectDependencies(Class<? extends IDependencyBean> dependingClass) {
		List<Class<? extends IDependencyBean>> dependentBeansClasses = new ArrayList<>();
		
		Instance<IDependencyBean> dependencyBeanInjector = CDIUtils.instances(IDependencyBean.class);
		
		for (IDependencyBean dependencyBeanInstance : dependencyBeanInjector) {
			List<Class<? extends IDependencyBean>> dependencyBeanInstanceDependencies = 
										ListUtils.newListOnNull(dependencyBeanInstance.getDependencies());
			
			if (ListUtils.listContains(dependencyBeanInstanceDependencies, (dependency) -> dependency.equals(dependingClass))) {
				dependentBeansClasses.add(dependencyBeanInstance.getClass());
			}
			
			dependencyBeanInjector.destroy(dependencyBeanInstance);
		}
		
		return filterEqualBeansClasses(dependentBeansClasses);
	}
	
	/**
	 * Get a class of each bean that depends direct on the dependingClassFilter parameter,
	 * and the dependent class is a instance of dependentClassFilter.
	 * 
	 * @param dependingClassFilter
	 * @param dependentClassFilter
	 * @return
	 */
	public static <T extends IDependencyBean> List<Class<? extends IDependencyBean>> getDirectDependencies(
																		Class<? extends IDependencyBean> dependingClassFilter, 
																		Class<T> dependentClassFilter) {
		List<Class<? extends IDependencyBean>> dependentBeansClasses = new ArrayList<>();
		
		Instance<T> dependencyBeanInjector = CDIUtils.instances(dependentClassFilter);
		
		for (T dependencyBeanInstance : dependencyBeanInjector) {
			List<Class<? extends IDependencyBean>> dependencies = ListUtils.newListOnNull(dependencyBeanInstance.getDependencies());
			
			if (ListUtils.listContains(dependencies, (dependency) -> (dependency.equals(dependingClassFilter)))) {
				dependentBeansClasses.add(dependencyBeanInstance.getClass());
			}
			
			dependencyBeanInjector.destroy(dependencyBeanInstance);
		}
		
		return filterEqualBeansClasses(dependentBeansClasses);
	}
	
	/**
	 * List the entire dependency web for the dependingClass parameter.
	 * That is, put the entire dependency web in a list.
	 * This method will iterate over dependent dependencies, and will go on
	 * until the entire web is listed.
	 * 
	 * @param dependingClass
	 * @return
	 */
	public static <T extends IDependencyBean> List<Class<? extends IDependencyBean>> completeDependencyWeb(Class<T> dependingClass) {
		T dependencyBeanInstance = CDIUtils.inject(dependingClass);
		
		List<Class<? extends IDependencyBean>> dependencyClasses = ListUtils.newListOnNull(dependencyBeanInstance.getDependencies());
		
		for (Class<? extends IDependencyBean> dependency : dependencyClasses) {
			ListUtils.addListContentToList(listDependencyWeb(dependency), dependencyClasses);
		}
		
		CDIUtils.destroy(dependingClass, dependencyBeanInstance);
		
		return filterEqualBeansClasses(dependencyClasses);
	}
	
	/**
	 * List the dependency web for a single dependent bean class.
	 * This method will iterate over dependent dependencies, and will go on
	 * until the entire web is listed.
	 * 
	 * @param <T>
	 * @param dependent
	 * @return
	 */
	public static <T extends IDependencyBean> List<Class<? extends IDependencyBean>> listDependencyWeb(Class<T> dependentClass) {
		T dependentBeanInstance = CDIUtils.inject(dependentClass);
		
		List<Class<? extends IDependencyBean>> dependencyClasses = ListUtils.newListOnNull(dependentBeanInstance.getDependencies());
		
		for (Class<? extends IDependencyBean> dependencyClass : dependencyClasses) {
			ListUtils.addListContentToList(listDependencyWeb(dependencyClass), dependencyClasses);
		}
		
		CDIUtils.destroy(dependentClass, dependentBeanInstance);
		
		return filterEqualBeansClasses(dependencyClasses);
	}
	
	/**
	 * List all dependencies that a dependency bean class depends on.
	 * @param <T>
	 * @param dependentClass
	 * @return
	 */
	public static <T extends IDependencyBean> List<Class<? extends IDependencyBean>> listDependencies(Class<T> dependentClass) {
		T dependentBeanInstance = CDIUtils.inject(dependentClass);
		
		List<Class<? extends IDependencyBean>> dependencies = ListUtils.newListOnNull(dependentBeanInstance.getDependencies());
		
		CDIUtils.destroy(dependentClass, dependentBeanInstance);
		
		return filterEqualBeansClasses(dependencies);
	}
	
	private static List<Class<? extends IDependencyBean>> filterEqualBeansClasses(List<Class<? extends IDependencyBean>> beanClasses) {
		return ListUtils.removeEquals(beanClasses, (left, right) -> left.equals(right));
	}
}