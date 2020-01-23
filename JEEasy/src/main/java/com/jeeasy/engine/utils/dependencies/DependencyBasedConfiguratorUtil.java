package com.jeeasy.engine.utils.dependencies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DependencyBasedConfiguratorUtil {
	private final List<DependencyGrouper> iterableDependencies = new ArrayList<>();
	
	public DependencyBasedConfiguratorUtil(Class<? extends IDependencyBean> dependencySuperclass) {
		for (Class<? extends IDependencyBean> dependentClass : DependencyBeanUtils.getDependencyBeans(dependencySuperclass)) {
			DependencyGrouper grouper = new DependencyGrouper();
			iterableDependencies.add(grouper);
			
			grouper.dependentClass = dependentClass;
			grouper.dependingClasses = DependencyBeanUtils.listDependencies(dependentClass);
		}
	}
	
	public Class<? extends IDependencyBean> getNextUnconfigured() {
		for (DependencyGrouper grouper : iterableDependencies) {
			if (grouper.dependingClasses.isEmpty())
				return grouper.dependentClass;
		}
		
		return null;
	}
	
	public void markConfigured(Class<? extends IDependencyBean> configuredClass) {
		Iterator<DependencyGrouper> dependencyIterator = iterableDependencies.iterator();
		
		while (dependencyIterator.hasNext()) {
			DependencyGrouper grouper = dependencyIterator.next();
			
			if (grouper.dependentClass.equals(configuredClass)) {
				dependencyIterator.remove();
			}
			
			else {
				Iterator<Class<? extends IDependencyBean>> dependingClassIterator = grouper.dependingClasses.iterator();
				
				while (dependingClassIterator.hasNext()) {
					Class<? extends IDependencyBean> dependingClass = dependingClassIterator.next();
					
					if (dependingClass.equals(configuredClass)) {
						dependingClassIterator.remove();
					}
				}
			}
		}
	}
}

class DependencyGrouper {
	public Class<? extends IDependencyBean> dependentClass;
	public List<Class<? extends IDependencyBean>> dependingClasses = new ArrayList<>();
}