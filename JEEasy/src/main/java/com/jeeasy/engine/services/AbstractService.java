package com.jeeasy.engine.services;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import com.jeeasy.engine.context.ArchitectureContext;
import com.jeeasy.engine.database.eaos.AbstractListingEAO;
import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.exceptions.FailureRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumFailureExceptionCodes;
import com.jeeasy.engine.queries.PagedResultList;
import com.jeeasy.engine.queries.QuerySettings;
import com.jeeasy.engine.queries.vos.AbstractVO;
import com.jeeasy.engine.services.validators.AbstractCRUDValidator;
import com.jeeasy.engine.translations.EnumFailuresTranslations;
import com.jeeasy.engine.translations.Translator;
import com.jeeasy.engine.utils.cdi.CDIUtils;
import com.jeeasy.engine.utils.data.ListUtils;
import com.jeeasy.engine.utils.dependencies.DependencyBeanUtils;
import com.jeeasy.engine.utils.dependencies.IDependencyBean;
import com.jeeasy.engine.utils.reflection.ClassUtils;
import com.jeeasy.engine.utils.reflection.ReflectionUtils;

public abstract class AbstractService<E extends AbstractEntity, V extends AbstractVO,  VL extends AbstractCRUDValidator<E>, 
									  						EAO extends AbstractListingEAO<E, V>> implements IDependencyBean {
	@Inject
	private EAO eao;
	
	@Inject
	private Translator translator;
	
	@Inject
	private ArchitectureContext context;
	
	@Inject
	private VL validator;
	
	private final String[] updateableFields;
	
	public AbstractService(String... updateableFields) {
		this.updateableFields = updateableFields;
	}
	
	public E save(E entity) {
		if (entity.containsId()) {
			return update(entity);
		}
		
		return insert(entity);
	}
	
	public E insert(E entity) {
		validator.validateInsert(entity);
		
		notifyPreInsert(entity);
		
		E persisted = eao.persist(entity);
		
		notifyPostInsert(persisted);
		
		return persisted;
	}

	public E find(Long entityId) {
		validator.validateFind(entityId);
		
		return eao.find(entityId);
	}
	
	public PagedResultList<V> defaultPagedSearch(QuerySettings querySettings) {
		validator.validatePagedSearch(querySettings);
		
		return eao.getDefaultPagedResultList(querySettings);
	}

	public E update(E entity) {
		E entityToUpdate = enforceUpdateableFields(entity);
		validator.validateUpdate(entityToUpdate);
		
		notifyPreUpdate(entityToUpdate);
		
		E updated = eao.merge(entityToUpdate);
		
		notifyPostUpdate(updated);
		
		return updated;
	}

	public void delete(Long entityId) {
		E entity = eao.find(entityId);
		validator.validateDelete(entity);
		
		notifyPreDelete(entity);
		
		eao.delete(entityId);
		
		notifyPostDelete(entity);
	}
	
	public void delete(List<Long> entityIds) {
		for (Long id : ListUtils.newListOnNull(entityIds)) {
			delete(id);
		}
	}
	
	public E enforceUpdateableFields(E received) {
		try {
			E entity = eao.find(received.getId());
			eao.detach(entity);
			
			for (String fieldName : updateableFields) {
				Field entityField = entity.getClass().getField(fieldName);
				
				Method receivedGetter = ReflectionUtils.findGetterForField(entity.getClass(), entityField);
				Method entitySetter = ReflectionUtils.findSetterForField(entity.getClass(), entityField);
				
				Object setValue;
				
				if (receivedGetter != null) {
					setValue = receivedGetter.invoke(received);
				}
				
				else {
					setValue = entityField.get(received);
				}
				
				if (entitySetter != null) {
					entitySetter.invoke(entity, setValue);
				}
				
				else {
					entityField.set(entity, setValue);
				}
			}
			
			return entity;
		} catch (Exception ex) {
			throw new FailureRuntimeException(
					translator.translateForContextLanguage(EnumFailuresTranslations.FAILED_TO_ENFORCE_UPDATEABLE_FIELD, 
							received.getClass().getSimpleName()),
					EnumFailureExceptionCodes.FAILED_TO_VALIDATE_DATA);
		}
	}

	public EAO getEAO() {
		return eao;
	}

	public Translator getTranslator() {
		return translator;
	}

	public VL getValidator() {
		return validator;
	}

	public String[] getUpdateableFields() {
		return updateableFields;
	}

	public ArchitectureContext getContext() {
		return context;
	}
	
	@SuppressWarnings("unchecked")
	private void notifyPreInsert(E persisted) {
		onPreInsert(persisted);
		
		iterateDependenciesServices((service) -> service.onServiceThatDependsOnThisPreInsert((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), persisted));
		iterateServicesThatDependsOnThis((service) -> service.onDependencyServicePreInsert((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), persisted));
	}
	
	@SuppressWarnings("unchecked")
	private void notifyPreUpdate(E updated) {
		onPreUpdate(updated);
		
		iterateDependenciesServices((service) -> service.onServiceThatDependsOnThisPreUpdate((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), updated));
		iterateServicesThatDependsOnThis((service) -> service.onDependencyServicePreUpdate((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), updated));
	}
	
	@SuppressWarnings("unchecked")
	private void notifyPreDelete(E deleted) {
		onPreDelete(deleted);
		
		iterateDependenciesServices((service) -> service.onServiceThatDependsOnThisPreDelete((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), deleted));
		iterateServicesThatDependsOnThis((service) -> service.onDependencyServicePreDelete((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), deleted));
	}
	
	@SuppressWarnings("unchecked")
	private void notifyPostInsert(E persisted) {
		onPostInsert(persisted);
		
		iterateDependenciesServices((service) -> service.onServiceThatDependsOnThisPostInsert((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), persisted));
		iterateServicesThatDependsOnThis((service) -> service.onDependencyServicePostInsert((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), persisted));
	}
	
	@SuppressWarnings("unchecked")
	private void notifyPostUpdate(E updated) {
		onPostUpdate(updated);
		
		iterateDependenciesServices((service) -> service.onServiceThatDependsOnThisPostUpdate((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), updated));
		iterateServicesThatDependsOnThis((service) -> service.onDependencyServicePostUpdate((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), updated));
	}
	
	@SuppressWarnings("unchecked")
	private void notifyPostDelete(E deleted) {
		onPostDelete(deleted);
		
		iterateDependenciesServices((service) -> service.onServiceThatDependsOnThisPostDelete((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), deleted));
		iterateServicesThatDependsOnThis((service) -> service.onDependencyServicePostDelete((Class<? extends AbstractService<?, ?, ?, ?>>) getClass(), deleted));
	}
	
	private void iterateDependenciesServices(Consumer<AbstractService<?, ?, ?, ?>> iterator) {
		List<Class<?>> dependencies = ClassUtils.castToGenerics(DependencyBeanUtils.listDependencies(getClass()));
		iterateDependencyServices(dependencies, iterator);
	}
	
	private void iterateServicesThatDependsOnThis(Consumer<AbstractService<?, ?, ?, ?>> iterator) {
		List<Class<?>> dependings = ClassUtils.castToGenerics(DependencyBeanUtils.getDirectDependencies(getClass()));
		iterateDependencyServices(dependings, iterator);
	}
	
	@SuppressWarnings("rawtypes")
	private void iterateDependencyServices(List<Class<?>> dependencyClasses, Consumer<AbstractService<?, ?, ?, ?>> iterator) {
		List<Class<AbstractService>> servicesClasses = ClassUtils.filterClassesInstancesOf(AbstractService.class, dependencyClasses);
		
		for (Class<AbstractService> serviceClass : servicesClasses) {
			AbstractService<?, ?, ?, ?> injectedService = CDIUtils.inject(serviceClass);
			
			iterator.accept(injectedService);
			
			CDIUtils.destroy(serviceClass, injectedService);
		}
	}
	
	protected void onServiceThatDependsOnThisPreInsert(Class<? extends AbstractService<?, ?, ?, ?>> dependentService, Object insertedEntity) {}
	
	protected void onServiceThatDependsOnThisPreDelete(Class<? extends AbstractService<?, ?, ?, ?>> dependentService, Object deletedEntity) {}
	
	protected void onServiceThatDependsOnThisPreUpdate(Class<? extends AbstractService<?, ?, ?, ?>> dependentService, Object updatedEntity) {}
	
	protected void onDependencyServicePreInsert(Class<? extends AbstractService<?, ?, ?, ?>> dependencyService, Object insertedEntity) {}
	
	protected void onDependencyServicePreDelete(Class<? extends AbstractService<?, ?, ?, ?>> dependencyService, Object deletedEntity) {}
	
	protected void onDependencyServicePreUpdate(Class<? extends AbstractService<?, ?, ?, ?>> dependencyService, Object updatedEntity) {}
	
	protected void onServiceThatDependsOnThisPostInsert(Class<? extends AbstractService<?, ?, ?, ?>> dependentService, Object insertedEntity) {}
	
	protected void onServiceThatDependsOnThisPostDelete(Class<? extends AbstractService<?, ?, ?, ?>> dependentService, Object deletedEntity) {}
	
	protected void onServiceThatDependsOnThisPostUpdate(Class<? extends AbstractService<?, ?, ?, ?>> dependentService, Object updatedEntity) {}
	
	protected void onDependencyServicePostInsert(Class<? extends AbstractService<?, ?, ?, ?>> dependencyService, Object insertedEntity) {}
	
	protected void onDependencyServicePostDelete(Class<? extends AbstractService<?, ?, ?, ?>> dependencyService, Object deletedEntity) {}
	
	protected void onDependencyServicePostUpdate(Class<? extends AbstractService<?, ?, ?, ?>> dependencyService, Object updatedEntity) {}
		
	protected void onPostInsert(E persisted) {}
	
	protected void onPreInsert(E entity) {}
	
	protected void onPreUpdate(E entity) {}
	
	protected void onPostUpdate(E updated) {}
	
	protected void onPreDelete(E entity) {}
	
	protected void onPostDelete(E deleted) {}
}