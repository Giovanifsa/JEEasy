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
	
	private void notifyPreInsert(E persisted) {
		onPreInsert(persisted);
		
		iterateDependentServices((service) -> service.onDependentServicePreInsert(getClass(), persisted));
		iterateDependingServices((service) -> service.onDependingServicePreInsert(getClass(), persisted));
	}
	
	private void notifyPreUpdate(E updated) {
		onPreUpdate(updated);
		
		iterateDependentServices((service) -> service.onDependentServicePreUpdate(getClass(), updated));
		iterateDependingServices((service) -> service.onDependingServicePreUpdate(getClass(), updated));
	}
	
	private void notifyPreDelete(E deleted) {
		onPreDelete(deleted);
		
		iterateDependentServices((service) -> service.onDependentServicePreDelete(getClass(), deleted));
		iterateDependingServices((service) -> service.onDependingServicePreDelete(getClass(), deleted));
	}
	
	private void notifyPostInsert(E persisted) {
		onPostInsert(persisted);
		
		iterateDependentServices((service) -> service.onDependentServicePostInsert(getClass(), persisted));
		iterateDependingServices((service) -> service.onDependingServicePostInsert(getClass(), persisted));
	}
	
	private void notifyPostUpdate(E updated) {
		onPostUpdate(updated);
		
		iterateDependentServices((service) -> service.onDependentServicePostUpdate(getClass(), updated));
		iterateDependingServices((service) -> service.onDependingServicePostUpdate(getClass(), updated));
	}
	
	private void notifyPostDelete(E deleted) {
		onPostDelete(deleted);
		
		iterateDependentServices((service) -> service.onDependentServicePostDelete(getClass(), deleted));
		iterateDependingServices((service) -> service.onDependingServicePostDelete(getClass(), deleted));
	}
	
	private void iterateDependentServices(Consumer<AbstractService<?, ?, ?, ?>> iterator) {
		List<Class<?>> dependings = ClassUtils.castToGenerics(DependencyBeanUtils.listDependencies(getClass()));
		iterateDependencyServices(dependings, iterator);
	}
	
	private void iterateDependingServices(Consumer<AbstractService<?, ?, ?, ?>> iterator) {
		List<Class<?>> dependents = ClassUtils.castToGenerics(DependencyBeanUtils.getDirectDependencies(getClass()));
		iterateDependencyServices(dependents, iterator);
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
	
	@SuppressWarnings("rawtypes")
	protected void onDependentServicePreInsert(Class<? extends AbstractService> dependentService, Object insertedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependentServicePreDelete(Class<? extends AbstractService> dependentService, Object deletedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependentServicePreUpdate(Class<? extends AbstractService> dependentService, Object updatedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependingServicePreInsert(Class<? extends AbstractService> dependentService, Object insertedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependingServicePreDelete(Class<? extends AbstractService> dependentService, Object deletedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependingServicePreUpdate(Class<? extends AbstractService> dependentService, Object updatedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependentServicePostInsert(Class<? extends AbstractService> dependentService, Object insertedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependentServicePostDelete(Class<? extends AbstractService> dependentService, Object deletedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependentServicePostUpdate(Class<? extends AbstractService> dependentService, Object updatedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependingServicePostInsert(Class<? extends AbstractService> dependentService, Object insertedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependingServicePostDelete(Class<? extends AbstractService> dependentService, Object deletedEntity) {}
	
	@SuppressWarnings("rawtypes")
	protected void onDependingServicePostUpdate(Class<? extends AbstractService> dependentService, Object updatedEntity) {}
		
	protected void onPostInsert(E persisted) {}
	
	protected void onPreInsert(E entity) {}
	
	protected void onPreUpdate(E entity) {}
	
	protected void onPostUpdate(E updated) {}
	
	protected void onPreDelete(E entity) {}
	
	protected void onPostDelete(E deleted) {}
}